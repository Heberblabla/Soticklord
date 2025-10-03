package Data
import kotlin.random.Random
import kotlin.math.ceil

class Rey_de_los_Gigantes(
    var nombre: String = "Rey_de_los_Gigantes",
    var vida: Int = 1500,
    var ataqueBase: Int = 50,
    var dañoCritico: Double = 1.5,
    var probabilidadCritico: Double = 0.50,
    var aereo: Boolean = false,
    var estadoVida: Boolean = true,
    var rutaViva: String = "recursos/rey/rey_de_los_gigantes.png",
    var rutaMuerta: String = "recursos/tropa_muerta.png",
    var turnoActivo: Boolean = true,
    var turnoDoble: Boolean = false
) {

    // Método privado que calcula el daño
    private fun calcularDaño(): Int {
        val suerte = Random.nextDouble()

        return if (suerte < probabilidadCritico) {
            ceil(ataqueBase * dañoCritico).toInt()
        } else {
            ataqueBase
        }
    }
}
