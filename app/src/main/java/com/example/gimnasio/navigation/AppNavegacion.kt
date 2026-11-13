package com.example.gimnasio.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gimnasio.data.actividades
import com.example.gimnasio.data.objetivos
import com.example.gimnasio.data.retosFlash
import com.example.gimnasio.ui.screens.*
import com.example.gimnasio.viewmodel.GimnasioViewModel


@Composable

fun AppNavegacion() {
    val navController = rememberNavController()
    val viewModel: GimnasioViewModel = viewModel()
    val progresoRetos by viewModel.progresoRetos.collectAsState()
    val favoritos by viewModel.favoritos.collectAsState()
    val alternarFavorito: (Int) -> Unit = { indice -> viewModel.alternarFavorito(indice) }
    val registrosFiltrados by viewModel.registrosFiltrados.collectAsState()
    val todosLosRegistros by viewModel.registros.collectAsState()
    val estadisticas by viewModel.estadisticas.collectAsState()
    val filtroObjetivo by viewModel.filtroObjetivo.collectAsState()



    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            GIMNASIO(
                onObjetivoClick = { indice -> navController.navigate("rutina/$indice") },
                onActividadClick = { indice -> navController.navigate("actividad/$indice") },
                onRetoClick = { indice -> navController.navigate("reto/$indice") },
                progresoRetos = progresoRetos,
                favoritos = favoritos,
                onFavoritoClick = alternarFavorito,
                onVerFavoritos = { navController.navigate("favoritos") },
                onVerDiario = { navController.navigate("diario") }
            )
        }
        // ----- Diario de entrenamiento -----
        composable("diario") {
            DiarioScreen(
                registros = registrosFiltrados,
                estadisticas = estadisticas,
                filtroObjetivo = filtroObjetivo,
                onFiltroChange = { viewModel.cambiarFiltro(it) },
                onNuevo = { navController.navigate("diario_nuevo") },
                onEditar = { id -> navController.navigate("diario_editar/$id") },
                onEliminar = { viewModel.eliminarRegistro(it) },
                onAlternarCompletado = { viewModel.alternarCompletado(it) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable("diario_nuevo") {
            FormularioRegistroScreen(
                registroInicial = null,
                onGuardar = {
                    viewModel.guardarRegistro(it)
                    navController.popBackStack()
                },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = "diario_editar/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val registro = todosLosRegistros.find { it.id == id }
            if (registro != null) {
                FormularioRegistroScreen(
                    registroInicial = registro,
                    onGuardar = {
                        viewModel.actualizarRegistro(it)
                        navController.popBackStack()
                    },
                    onVolver = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = "rutina/{indice}",
            arguments = listOf(navArgument("indice") { type = NavType.IntType })
        ) { backStackEntry ->
            val indice = backStackEntry.arguments?.getInt("indice") ?: 0
            RutinaScreen(
                objetivo = objetivos[indice],
                esFavorito = favoritos.contains(indice),
                onFavoritoClick = { alternarFavorito(indice) },
                onDiaClick = { indiceDia -> navController.navigate("dia/$indice/$indiceDia") },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = "reto/{indice}",
            arguments = listOf(navArgument("indice") { type = NavType.IntType })
        ) { backStackEntry ->
            val indice = backStackEntry.arguments?.getInt("indice") ?: 0
            RetoScreen(
                reto = retosFlash[indice],
                racha = progresoRetos.sum(),
                diasCompletados = progresoRetos[indice],
                onCompletarDia = { viewModel.completarDiaReto(indice) },
                onReiniciar = { viewModel.reiniciarReto(indice) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable("favoritos") {
            FavoritosScreen(
                favoritos = favoritos,
                onRutinaClick = { indice -> navController.navigate("rutina/$indice") },
                onQuitarFavorito = alternarFavorito,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = "actividad/{indice}",
            arguments = listOf(navArgument("indice") { type = NavType.IntType })
        ) { backStackEntry ->
            val indice = backStackEntry.arguments?.getInt("indice") ?: 0
            ActividadScreen(
                actividad = actividades[indice],
                onVolver = { navController.popBackStack() }
            )
        }
        // NUEVA pantalla: ejercicios del día
        composable(
            route = "dia/{objetivo}/{dia}",
            arguments = listOf(
                navArgument("objetivo") { type = NavType.IntType },
                navArgument("dia") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val iObjetivo = backStackEntry.arguments?.getInt("objetivo") ?: 0
            val iDia = backStackEntry.arguments?.getInt("dia") ?: 0
            DiaScreen(
                objetivo = objetivos[iObjetivo],
                dia = objetivos[iObjetivo].dias[iDia],
                onVolver = { navController.popBackStack() }
            )
        }
    }
}
