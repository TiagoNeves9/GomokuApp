package com.example.pdm2324i_gomoku_g37.Authors


data class Author(val number: Int, val name: String, val desc: String, val img: String)

val authors: ArrayList<Author> = arrayListOf(
    Author(48292, "Tiago Neves", "O melhor programador", "img_tiago"),
    Author(48333, "Tomás Barroso", "O programador mediano", "img_tomas"),
    Author(48264, "João Pereira", "O pior programador", "img_joao"),
)