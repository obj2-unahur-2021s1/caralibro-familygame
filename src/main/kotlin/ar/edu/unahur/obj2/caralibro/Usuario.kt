package ar.edu.unahur.obj2.caralibro

class Usuario {
  private var publicaciones = mutableListOf<Publicacion>()

  private var amigos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
    publicacion.propietario = this
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun puedeVerPublicacionDe(publicacion: Publicacion) : Boolean {
    return (publicacion.permisos == Permisos.PUBLICA
            || (publicacion.permisos == Permisos.EXCLUIDOS && !publicacion.listaExcluidos.contains(this))
            || (publicacion.permisos == Permisos.PERMITIDOS && publicacion.listaPermitidos.contains(this))
            || (publicacion.permisos == Permisos.PRIVADA && publicacion.propietario?.amigos?.contains(this) ?: false))
  }

  fun agregarAmigo(usuarioAmigo: Usuario) {
    amigos.add(usuarioAmigo)
  }

  fun masAmistosoQue(otroUsuario : Usuario) = this.amigos.size > otroUsuario.amigos.size

  fun listaMejoresAmigos() : MutableList<Usuario> {
    var listaMejoresAmigos = this.amigos
    val listaPersonasExcluidas = listaTotalDePersonasExcluidas()
    for (personaExcluida in listaPersonasExcluidas){
      if (listaMejoresAmigos.contains(personaExcluida)){
        listaMejoresAmigos.remove(personaExcluida)
      }
    }
    return listaMejoresAmigos
  }

   private fun listaTotalDePersonasExcluidas() : MutableList<Usuario> {
    var listaExcluidos = mutableListOf<Usuario>()
    for(publicaciones in this.publicaciones){
      for (excluidos in publicaciones.listaExcluidos){
        listaExcluidos.add(excluidos)
      }
    }
    return listaExcluidos
  }

  private fun cantidadDeLikes() = this.publicaciones.sumOf { it.likes }

  fun amigoMasPopular() = this.amigos.maxByOrNull { it.cantidadDeLikes() }

  fun stalkeaA(usuario:Usuario) =
    usuario.publicaciones.count { it.recibioLikesDe(this) } >= usuario.publicaciones.size * 0.9
}
