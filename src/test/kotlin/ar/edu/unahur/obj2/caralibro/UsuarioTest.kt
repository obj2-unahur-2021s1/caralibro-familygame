package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldNotContain
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
        it("permite cambiar la compresion"){
          cambiarFactorDeCompresion(0.8)
          FACTOR_COMPRESION.shouldBe(0.8)
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
        juan.agregarPublicacion(fotoEnCuzco)
        val carlos = Usuario()

        it("Publico"){
          fotoEnCuzco.agregarPermisos(Permisos.PUBLICA)
          carlos.puedeVerPublicacionDe(fotoEnCuzco).shouldBeTrue()
        }

        it("Privado sin estar en lista de amigos"){
          fotoEnCuzco.agregarPermisos(Permisos.PRIVADA)
          carlos.puedeVerPublicacionDe(fotoEnCuzco).shouldBeFalse()
        }
        it("Privado estando en lista de amigos"){
          fotoEnCuzco.agregarPermisos(Permisos.PRIVADA)
          juan.agregarAmigo(carlos)
          carlos.puedeVerPublicacionDe(fotoEnCuzco).shouldBeTrue()
        }
        it("Privado con lista de permitidos"){
          val listaPermitidos = mutableListOf(carlos)
          fotoEnCuzco.agregarPermisos(Permisos.PERMITIDOS, listaPermitidos)
          carlos.puedeVerPublicacionDe(fotoEnCuzco).shouldBeTrue()
        }
        it("Publico con lista de excluidos"){
          val listaExcluidos = mutableListOf(carlos)
          fotoEnCuzco.agregarPermisos(Permisos.EXCLUIDOS, listaExcluidos)
          carlos.puedeVerPublicacionDe(fotoEnCuzco).shouldBeFalse()
        }
      }
    }

    describe("Un usuario") {
      val juana = Usuario()
      val jose = Usuario()
      it("puede calcular el espacio que ocupan sus publicaciones") {
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(629191)
      }
      it("Es mas amistoso que otro"){
        val carlos = Usuario()
        val jorge = Usuario()
        juana.agregarAmigo(carlos)
        juana.agregarAmigo(jorge)
        juana.masAmistosoQue(jose).shouldBeTrue()
      }
      it("mejores amigos"){
        val carlos = Usuario()
        val jorge = Usuario()
        val agustin = Usuario()
        juana.agregarAmigo(carlos)
        juana.agregarAmigo(jorge)
        juana.agregarAmigo(jose)
        juana.agregarAmigo(agustin)
        juana.agregarPublicacion(fotoEnCuzco)
        val listaExcluidos = mutableListOf(agustin)
        fotoEnCuzco.agregarPermisos(Permisos.EXCLUIDOS,listaExcluidos)
        juana.listaMejoresAmigos().shouldNotContain(agustin)
      }
      it("amigo mas popular"){
        val agustin = Usuario()
        val jorge = Usuario()
        val foto1 = Foto(750, 1024)
        val foto2 = Foto(1080, 1920)
        jorge.agregarPublicacion(foto2)
        agustin.agregarPublicacion(foto1)
        foto1.recibirMeGusta(jose)
        foto1.recibirMeGusta(jorge)
        foto2.recibirMeGusta(agustin)
        juana.agregarAmigo(agustin)
        juana.agregarAmigo(jorge)
        juana.amigoMasPopular().shouldBe(agustin)
      }
      it("Stalkea a un usuario:"){
        val agustin = Usuario()
        val jorge = Usuario()
        val foto1 = Foto(750, 1024)
        val foto2 = Foto(1080, 1920)
        val foto3 = Foto(750, 1024)
        val foto4 = Foto(1080, 1920)
        val foto5 = Foto(1080, 1920)
        val foto6 = Foto(1080, 1920)
        val foto7 = Foto(1080, 1920)
        val foto8 = Foto(1080, 1920)
        val foto9 = Foto(1080, 1920)
        val foto10 = Foto(1080, 1920)

        agustin.agregarPublicacion(foto1)
        agustin.agregarPublicacion(foto2)
        agustin.agregarPublicacion(foto3)
        agustin.agregarPublicacion(foto4)
        agustin.agregarPublicacion(foto5)
        agustin.agregarPublicacion(foto6)
        agustin.agregarPublicacion(foto7)
        agustin.agregarPublicacion(foto8)
        agustin.agregarPublicacion(foto9)
        agustin.agregarPublicacion(foto10)

        foto1.recibirMeGusta(jorge)
        foto2.recibirMeGusta(jorge)
        foto3.recibirMeGusta(jorge)
        foto4.recibirMeGusta(jorge)
        foto5.recibirMeGusta(jorge)
        foto6.recibirMeGusta(jorge)
        foto7.recibirMeGusta(jorge)
        foto8.recibirMeGusta(jorge)
        foto9.recibirMeGusta(jorge)

        jorge.stalkeaA(agustin).shouldBeTrue()
      }
    }
  }
})
