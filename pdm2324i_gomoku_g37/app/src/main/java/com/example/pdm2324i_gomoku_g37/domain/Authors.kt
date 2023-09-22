package com.example.pdm2324i_gomoku_g37.domain

data class Authors(val name: String, val desc : String, val img : String){
    val authors = arrayListOf<Authors>(
        Authors("Tiago Neves","O melhor programador", "imagem do linkedIn"),
        Authors("Tomás Barroso","O programador mediano", "imagem do linkedIn"),
        Authors("João Pereira","O pior programador", "imagem do linkedIn"),
        )
}