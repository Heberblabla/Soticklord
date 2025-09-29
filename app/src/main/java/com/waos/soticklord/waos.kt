package com.waos.soticklord

import java.sql.DriverManager

fun main() {
    val url = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:6543/postgres"
    val user = "postgres.zropeiibzqefzjrkdzzp"
    val password = "Tuhermana1233456789"

    try {
        // Conexión a la base de datos
        val conexion = DriverManager.getConnection(url, user, password)
        println(" Conectado a Supabase PostgreSQL")

        // Consulta: Tropas del jugador 3
        val tropasQuery = """
        SELECT tt.nombre AS tipo, tj.nombre, tj.nivel
        FROM tropas_jugador tj
        JOIN tipos_tropa tt ON tj.id_tipo = tt.id_tipo
        WHERE tj.id_jugador = 3;
    """.trimIndent()

        val tropasStmt = conexion.createStatement()
        val tropasResult = tropasStmt.executeQuery(tropasQuery)

        println("Tropas del jugador 3:")
        while (tropasResult.next()) {
            val tipoTropa = tropasResult.getString("tipo")
            val nombreTropa = tropasResult.getString("nombre")
            val nivelTropa = tropasResult.getInt("nivel")
            println("Tipo: $tipoTropa, Nombre: $nombreTropa, Nivel: $nivelTropa")
        }

        // Consulta: Lista de jugadores
        val jugadoresQuery = """
        SELECT id_jugador, nombre_usuario
        FROM jugadores;
    """.trimIndent()

        val jugadoresStmt = conexion.createStatement()
        val jugadoresResult = jugadoresStmt.executeQuery(jugadoresQuery)

        println("\nLista de jugadores:")
        while (jugadoresResult.next()) {
            val idJugador = jugadoresResult.getInt("id_jugador")
            val nombreJugador = jugadoresResult.getString("nombre_usuario")
            println("ID: $idJugador, Nombre: $nombreJugador")
        }

        // Cerrar conexión
        conexion.close()

    } catch (error: Exception) {
        error.printStackTrace()
    }

}
