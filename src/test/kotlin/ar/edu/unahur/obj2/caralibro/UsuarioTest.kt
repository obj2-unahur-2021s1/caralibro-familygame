package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import ar.edu.unahur.obj2.caralibro.Texto
import ar.edu.unahur.obj2.caralibro.Foto
import ar.edu.unahur.obj2.caralibro.Video
import ar.edu.unahur.obj2.caralibro.Usuario

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val videoCasamiento = Video(120, Calidad.SD)

    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }
      describe("de tipo video") {
        it("ocupa lo mismo que su duracion en segundos siendo SD"){
          videoCasamiento.espacioQueOcupa().shouldBe(120)
        }
        it("ocupa el triple que su duracion en segundos siendo HD 720p"){
          videoCasamiento.calidad = Calidad.HD720p
          videoCasamiento.espacioQueOcupa().shouldBe(360)
        }
        it("ocupa 5 veces la duracion en segundos siendo HD 1080p"){
          videoCasamiento.calidad = Calidad.HD1080p
          videoCasamiento.espacioQueOcupa().shouldBe(600)
        }
        it("cambia correctamente la calidad"){
          videoCasamiento.cambiarCalidad(Calidad.HD1080p)
          videoCasamiento.calidad.shouldBe(Calidad.HD1080p)
          videoCasamiento.espacioQueOcupa().shouldBe(600)
        }
      }
    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = ar.edu.unahur.obj2.caralibro.Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(550548)
      }
    }
  }
})
