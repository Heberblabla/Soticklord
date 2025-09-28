package com.waos.soticklord

import java.sql.DriverManager

fun main() {
    val url = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:6543/postgres"
    val user = "postgres.zropeiibzqefzjrkdzzp"
    val password = "Tuhermana1233456789"

    try {
        // Conexión
        val conn = DriverManager.getConnection(url, user, password)
        println("✅ Conectado a Supabase PostgreSQL")


        val stmt1 = conn.createStatement()
        val rs1 = stmt1.executeQuery("""
            SELECT tt.nombre AS tipo, tj.nombre, tj.nivel
            FROM tropas_jugador tj
            JOIN tipos_tropa tt ON tj.id_tipo = tt.id_tipo
            WHERE tj.id_jugador = 3;
        """.trimIndent())

        println("Tropas del jugador 3:")
        while (rs1.next()) {
            val tipo = rs1.getString("tipo")
            val nombre = rs1.getString("nombre")
            val nivel = rs1.getInt("nivel")
            println("Tipo: $tipo, Nombre: $nombre, Nivel: $nivel")
        }

        // Query 2
        val stmt2 = conn.createStatement()
        val rs2 = stmt2.executeQuery("""
            SELECT id_jugador, nombre_usuario
            FROM jugadores;
        """.trimIndent())

        println("\nLista de jugadores:")
        while (rs2.next()) {
            val id = rs2.getInt("id_jugador")
            val nombre = rs2.getString("nombre_usuario")
            println("ID: $id, Nombre: $nombre")
        }

        conn.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
