package com.nyakshoot.dnd.ui.navigation.graphs

sealed class Graph(val route: String){
    object RootGraph: Graph("root_graph")
    object AuthGraph: Graph("auth_graph")
    object HomeGraph: Graph("home_graph")
}
