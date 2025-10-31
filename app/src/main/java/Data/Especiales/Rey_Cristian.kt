package Data.Especiales

import Data.Tropa
import android.provider.Settings
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Cristian (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Cristian",
        nivel = Nivel,
        vida = calcularVida(1000,Nivel),
        ataque_base = calcularAtaque(150,Nivel),
        daño_critico = calcularDañoCritico(1.5,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.65,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_cristian,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100

    ), Serializable {
    var esInmuneTotal = false
    var unaves = true

    override var vida: Int = calcularVida(1500, Nivel)
        set(value) {
            if (esInmuneTotal && value < field) {
                return
            }
            field = value
        }
    override var ataque_base: Int = calcularAtaque(100, Nivel)
        set(value) {
            if (esInmuneTotal && value < field) {
                return
            }
            field = value
        }

    override var precision: Int = 100
        set(value) {
            if (esInmuneTotal && value < field) {
                return
            }
            field = value
        }


    override fun toString(): String {
        return """
            Nombre: $nombre
            Nivel: $nivel
            Vida: $vida
            Ataque base: $ataque_base
            Daño crítico: $daño_critico
            Prob. crítico: $probabilidad_de_critico
            Aéreo: $aereo
            Estado vida: $estado_de_vida
            Turno activo: $turnoActivo
            Turno doble: $turnoDoble
        """.trimIndent()
    }

    private fun daño(): Int {
        val random = Random.Default
        val suerte = random.nextDouble()

        return if (suerte < this.probabilidad_de_critico) {
            val x = this.ataque_base * this.daño_critico
            ceil(x).toInt() // redondea hacia arriba
        } else {
            this.ataque_base // golpe normal
        }
    }


    fun Back_on_de_bit(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        for (i in enemigos.indices) {
            var daño = (enemigos[i].vida * 0.15).toInt()
            enemigos[posicion]!!.vida = enemigos[posicion]!!.vida - daño
        }

        for (i in enemigos.indices) {
            enemigos[i].ataque_base -= (enemigos[1].ataque_base * 0.15).toInt()
        }

    }

    fun king_Crimson(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(Waos){
            var i = 0
            while(true){
                if(GlobalData.Diccionario_Tropas[i]!!.nombre == "Tropa_Gigante_estelar"){
                    break
                }
                i ++
            }
            var j = 0
            while(true){
                if(GlobalData.Diccionario_Tropas[j]!!.nombre == "Tropa_Gurandera"){
                    break
                }
                j ++
            }

            GlobalData.Jugador1[1] = GlobalData.Diccionario_Tropas[j]!!.clonar()
            GlobalData.Jugador1[2] = GlobalData.Diccionario_Tropas[j]!!.clonar()
            GlobalData.Jugador1[3] = GlobalData.Diccionario_Tropas[i]!!.clonar()
            GlobalData.Jugador1[4] = GlobalData.Diccionario_Tropas[i]!!.clonar()
            GlobalData.Jugador1[5] = GlobalData.Diccionario_Tropas[i]!!.clonar()
        }
        if(!Waos){
            var i = 0
            while(true){
                if(GlobalData.Diccionario_Tropas[i]!!.nombre == "Tropa_Gigante_estelar"){
                    break
                }
                i ++
            }
            var j = 0
            while(true){
                if(GlobalData.Diccionario_Tropas[j]!!.nombre == "Tropa_Gurandera"){
                    break
                }
                j ++
            }

            GlobalData.Jugador2[1] = GlobalData.Diccionario_Tropas[j]!!.clonar()
            GlobalData.Jugador2[2] = GlobalData.Diccionario_Tropas[j]!!.clonar()
            GlobalData.Jugador2[3] = GlobalData.Diccionario_Tropas[i]!!.clonar()
            GlobalData.Jugador2[4] = GlobalData.Diccionario_Tropas[i]!!.clonar()
            GlobalData.Jugador2[5] = GlobalData.Diccionario_Tropas[i]!!.clonar()
        }


        //invocas 2 curadneros atras 150pv 50atq
        //curan 100pv a todos expeto a ellosmismo

        //invocas 3 gigantes estelares, 500pv y 100ataque

        //solo si no tienes ninguna tropa viva y ni el rival
    }

    fun TUSK(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(Waos) {
            if (!GlobalData.Jugador1[1]!!.estado_de_vida &&
                !GlobalData.Jugador1[2]!!.estado_de_vida &&
                !GlobalData.Jugador1[3]!!.estado_de_vida &&
                !GlobalData.Jugador1[4]!!.estado_de_vida &&
                !GlobalData.Jugador1[5]!!.estado_de_vida
            ) {

                GlobalData.Jugador1[1] = GlobalData.Jugador2[1]!!.clonar()
                GlobalData.Jugador1[2] = GlobalData.Jugador2[2]!!.clonar()
                GlobalData.Jugador1[3] = GlobalData.Jugador2[3]!!.clonar()
                GlobalData.Jugador1[4] = GlobalData.Jugador2[4]!!.clonar()
                GlobalData.Jugador1[5] = GlobalData.Jugador2[5]!!.clonar()

                GlobalData.Jugador2[1]!!.vida -= GlobalData.Jugador2[1]!!.vida
                GlobalData.Jugador2[2]!!.vida -= GlobalData.Jugador2[2]!!.vida
                GlobalData.Jugador2[3]!!.vida -= GlobalData.Jugador2[3]!!.vida
                GlobalData.Jugador2[4]!!.vida -= GlobalData.Jugador2[4]!!.vida
                GlobalData.Jugador2[5]!!.vida -= GlobalData.Jugador2[5]!!.vida
            }
        }
        if(!Waos) {
            if (!GlobalData.Jugador2[1]!!.estado_de_vida &&
                !GlobalData.Jugador2[2]!!.estado_de_vida &&
                !GlobalData.Jugador2[3]!!.estado_de_vida &&
                !GlobalData.Jugador2[4]!!.estado_de_vida &&
                !GlobalData.Jugador2[5]!!.estado_de_vida
            ) {

                GlobalData.Jugador2[1] = GlobalData.Jugador1[1]!!.clonar()
                GlobalData.Jugador2[2] = GlobalData.Jugador1[2]!!.clonar()
                GlobalData.Jugador2[3] = GlobalData.Jugador1[3]!!.clonar()
                GlobalData.Jugador2[4] = GlobalData.Jugador1[4]!!.clonar()
                GlobalData.Jugador2[5] = GlobalData.Jugador1[5]!!.clonar()

                GlobalData.Jugador1[1]!!.vida -= GlobalData.Jugador1[1]!!.vida
                GlobalData.Jugador1[2]!!.vida -= GlobalData.Jugador1[2]!!.vida
                GlobalData.Jugador1[3]!!.vida -= GlobalData.Jugador1[3]!!.vida
                GlobalData.Jugador1[4]!!.vida -= GlobalData.Jugador1[4]!!.vida
                GlobalData.Jugador1[5]!!.vida -= GlobalData.Jugador1[5]!!.vida
            }
        }
        //si todas las tropas aliadas estan muertas , las tropas enemigas se pasan a tu bando

    }

    fun Diamond(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        var num = Random.nextInt(100)
        if(Waos) {
            if (num < 50) {
                var contador = 0
                while (true) {
                    var numero = Random.nextInt(1, 6)
                    if (GlobalData.Jugador1[numero]!!.vida <= 0) {
                        GlobalData.Jugador1[numero]!!.vida += 250
                        var nums = Random.nextInt(100)
                        if (nums < 5) {
                            GlobalData.Jugador1[numero]!!.vida += 250
                        }
                        break
                    } else {
                        contador ++
                        if(contador == 100){
                            break
                        }
                    }
                }
            }
        }
        if(Waos) {
            if (num < 50) {
                var contador = 0
                while (true) {
                    var numero = Random.nextInt(1, 6)
                    if (GlobalData.Jugador2[numero]!!.vida <= 0) {
                        GlobalData.Jugador2[numero]!!.vida += 250
                        var nums = Random.nextInt(100)
                        if (nums < 5) {
                            GlobalData.Jugador2[numero]!!.vida += 250
                        }
                        break
                    } else {
                        contador ++
                        if(contador == 100){
                            break
                        }
                    }
                }
            }
        }
    }

    fun Calamidad(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        val tropa = enemigos[posicion] ?: return
        val nombreClase = tropa.nombre
        val clase = GlobalData.Diccionario_Clases[nombreClase]

        if (clase != null) {
            // Crear una nueva instancia de esa clase con nivel 1
            val nuevaTropa = clase.constructors.first().call(1) as Tropa
            // Mantener posición y/o dueño si es necesario
            enemigos[posicion] = nuevaTropa
        }

    }

    fun Killer_Queen(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val tropa = enemigos[posicion] ?: return

        val clase = GlobalData.Diccionario_Clases[tropa.nombre]
        val copia = clase?.constructors?.first()?.call(tropa.nivel) as? Tropa ?: return
        val vidaMaxima = copia.vida

        // Si la tropa tiene menos vida que su máximo, la restaura
        if (tropa.vida < vidaMaxima) {
            tropa.vida = vidaMaxima
            tropa.precision -= 20
        }

        // Buscar una tropa enemiga diferente al "posicion" y eliminarla
        var contador = 0
        while (contador < 100) { // evita bucle infinito
            val nums = Random.nextInt(1, 6)
            val objetivo = enemigos[nums]

            if (nums != posicion && objetivo?.estado_de_vida == true) {
                objetivo.vida = 0
                objetivo.estado_de_vida = false
                println("💀 ${objetivo.nombre} ha sido destruido por Killer Queen!")
                break
            }
            contador++
        }
    }

    fun Golden(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(this.unaves) {
            esInmuneTotal = true
            this.unaves = false
        }
    }

    override fun Habilidad_Especial(Waos: Boolean) {
        // Seleccionar jugador y enemigo según el turno
        if(this.vida <= 0){
            return
        }
        val (yo, enemigo) = if (Waos) {
            GlobalData.Jugador1 to GlobalData.Jugador2
        } else {
            GlobalData.Jugador2 to GlobalData.Jugador1
        }

        // Función interna para contar cuántas tropas vivas hay (del 1 al 5)
        fun contarVivas(lista: ArrayList<Tropa?>): Int {
            var vivas = 0
            for (i in 1..5) {
                if (lista[i]?.estado_de_vida == true) vivas++
            }
            return vivas
        }

        var vivasmias = contarVivas(yo)
        var vivasenemigas = contarVivas(enemigo)

        // Mientras el enemigo tenga más tropas vivas, eliminar una aleatoria
        while (vivasmias < vivasenemigas) {
            val num = Random.nextInt(1, 6)
            val tropaEnemiga = enemigo[num]

            if (tropaEnemiga?.estado_de_vida == true) {
                tropaEnemiga.vida = 0
                tropaEnemiga.estado_de_vida = false

            }

            // Recontar tropas vivas después de cada cambio
            vivasmias = contarVivas(yo)
            vivasenemigas = contarVivas(enemigo)

            // Si ya no quedan tropas enemigas vivas, romper bucle
            if (enemigo.all { it == null || it.estado_de_vida == false }) break
        }


    }


    override fun clonar(): Tropa {
        val copia = Rey_Cristian(this.nivel)
        copia.nombre = this.nombre
        copia.vida = this.vida
        copia.ataque_base = this.ataque_base
        copia.daño_critico = this.daño_critico
        copia.probabilidad_de_critico = this.probabilidad_de_critico
        copia.aereo = this.aereo
        copia.estado_de_vida = this.estado_de_vida
        copia.rutaviva = this.rutaviva
        copia.rutamuerta = this.rutamuerta
        copia.turnoActivo = this.turnoActivo
        copia.turnoDoble = this.turnoDoble
        copia.cantidad_espinas = this.cantidad_espinas
        copia.cantidad_escudos = this.cantidad_escudos
        return copia
    }

    override fun Recivir_daño(tropa: Tropa,Ataque :Int) {
        if(this.cantidad_escudos > 0){
            this.vida -= (Ataque - (Ataque * cantidad_escudos)).toInt()
        }
        if(this.cantidad_espinas > 0){
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            return
        }

        this.vida -= Ataque
        return
    }

}