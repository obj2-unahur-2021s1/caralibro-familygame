package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe


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

      describe("Me gustas"){
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        val jorge = Usuario()
        fotoEnCuzco.recibirMeGusta(jorge)
        it("Cantidad de me gustas"){
          fotoEnCuzco.cantidadDeMeGustas().shouldBe(1)
        }
        it("Lista de personas que dieron Me Gusta"){
          val juan = Usuario()
          fotoEnCuzco.recibirMeGusta(juan)
          val lista = mutableListOf(jorge, juan)
          fotoEnCuzco.personasALasQueLesGusta().containsAll(lista)
        }
      }

      describe("Permisos"){
        val juan = Usuario()

        it("Publico"){
          fotoEnCuzco.permisos(Permisos.Publica)
          juan.puedeVerPublicacion(fotoEnCuzco).shouldBeTrue()
        }
        it("Privado sin estar en lista de amigos"){
          fotoEnCuzco.permisos(Permisos.Privada)
          juan.agregarPublicacion(fotoEnCuzco)
          val jorge = Usuario()
          jorge.puedeVerPublicacion(fotoEnCuzco).shouldBeFalse()
        }
        it("Privado estando en lista de amigos"){
          fotoEnCuzco.permisos(Permisos.Privada)
          juan.agregarPublicacion(fotoEnCuzco)
          val jorge = Usuario()
          jorge.puedeVerPublicacion(fotoEnCuzco).shouldBeTrue()
        }
        it("Privado con lista de permitidos"){
          val jorge = Usuario()
          val lista = mutableListOf<Usuario>()
          fotoEnCuzco.permisos(Permisos.Permitidos(lista))
          juan.agregarPublicacion(fotoEnCuzco)
          jorge.puedeVerPublicacion(fotoEnCuzco).shouldBeFalse()
        }
        it("Publico con lista de excluidos"){
          val jorge = Usuario()
          val lista = mutableListOf<Usuario>()
          fotoEnCuzco.permisos(Permisos.Excluidos(lista))
          juan.agregarPublicacion(fotoEnCuzco)
          jorge.puedeVerPublicacion(fotoEnCuzco).shouldBeTrue()
        }
      }
    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(550548)
      }
    }
  }
})
