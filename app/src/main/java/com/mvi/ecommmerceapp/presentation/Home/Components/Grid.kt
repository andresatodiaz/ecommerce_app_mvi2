package com.mvi.ecommmerceapp.presentation.Home.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mvi.ecommmerceapp.domain.Entities.Producto
import com.mvi.ecommmerceapp.domain.Entities.Usuario
import com.mvi.ecommmerceapp.ui.theme.colorMatrix

@Composable
fun Grid(
    usuario:Usuario,
    productos:List<Producto>,
    refreshing:Boolean,
    id:String,
    navController:NavController,
    selectedProducto:MutableState<Producto>,
    selectedProductoUrl:MutableState<String>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top=10.dp,start=10.dp,end=10.dp,bottom=80.dp)
    ){
        item(
            span={ GridItemSpan(2) }
        ){
            Column {
                Spacer(Modifier.padding(bottom=5.dp))
                UserBanner(usuario = usuario)
                Spacer(Modifier.padding(bottom=10.dp))
                HomeBanner(colorMatrix = colorMatrix)
                Spacer(Modifier.padding(bottom=20.dp))
            }
        }
        if(productos.isEmpty()){
            item{
               EmptyProducts(refreshing = refreshing)
            }
        }else{
            productos.forEachIndexed { index, producto ->
                item{
                   GridProduct(
                       index = index,
                       producto=producto,
                       selectedProducto = selectedProducto,
                       selectedProductoUrl =selectedProductoUrl,
                       navController = navController,
                       id= id,
                       )
                }
            }
        }
    }
}