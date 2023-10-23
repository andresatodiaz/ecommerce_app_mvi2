package com.example.ecommmerceapp.presentation.Home.ViewModel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.data.Entities.Usuario
import com.example.ecommmerceapp.data.Service.ProductoService
import com.example.ecommmerceapp.data.Service.UsuarioService
import com.example.ecommmerceapp.data.repository.ProductoRepository
import com.example.ecommmerceapp.data.repository.UsuarioRepository
import com.example.ecommmerceapp.presentation.Home.Intent.HomeContract
import com.example.ecommmerceapp.presentation.Perfil.Intent.PerfilContract
import com.example.ecommmerceapp.utils.MemoryConsumption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productoService: ProductoService,
    private val usuarioService: UsuarioService,
    private val productoRepository: ProductoRepository,
    private val usuarioRepository: UsuarioRepository
): ViewModel(), HomeContract {
    val productos=mutableStateOf(emptyList<Producto>())
    val productosList = mutableStateOf(emptyList<Producto>())
    val isLoading = mutableStateOf(false)
    val vendedorProducto = mutableStateOf(Usuario())
    val loadingVendedor= mutableStateOf(false)
    val vendedorEmpty = mutableStateOf(false)
    val misProductos= mutableStateOf(emptyList<Producto>())

    val initialLoad = mutableStateOf(false)

    val myUser = mutableStateOf(Usuario())

    val refreshing = mutableStateOf(true)

    private val mutableState = MutableStateFlow(HomeContract.State())
    override val state: StateFlow<HomeContract.State> =
        mutableState.asStateFlow()

    private val effectFlow = MutableSharedFlow<HomeContract.Effect>()
    override val effect: SharedFlow<HomeContract.Effect> =
        effectFlow.asSharedFlow()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun event(event: HomeContract.Event) = when(event){
        is HomeContract.Event.onGetProductos->
            getData()
        HomeContract.Event.onRefresh -> getData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(
    ){
        refreshing.value=true
        viewModelScope.launch {
            val startTime = LocalTime.now()
            val usuario = usuarioRepository.getMyUser()
            val productos = productoRepository.getProductos()!!

            mutableState.update {
                HomeContract.State(
                    usuario = usuario,
                    productos= productos,
                )
            }
            refreshing.value=false
            Log.i("comp-ExecutionTime", Duration.between(startTime,LocalTime.now()).toMillis().toString())
            Log.i("comp-MemoryConsump", MemoryConsumption().getUsedMemorySize().toString())
        }

    }



   /* private  suspend fun getProductosContract() = GetProductosUseCase()
        .catch {exception->
            Log.e("Error obteniendo productos",exception.message.toString())
        }
        .onEach {productos->
            mutableState.update {
                HomeContract.State(
                    productos = productos,
                    refreshing = false
                )
            }
        }
        .launchIn(viewModelScope)*/


    fun getProductos(){
        viewModelScope.launch {
            isLoading.value=true
           if( productoService.getProductos() !=null){
               productos.value= productoService.getProductos()!!.sortedBy {producto->
                   producto.precio.toInt()
               }
           }
            isLoading.value=false
        }
    }

    fun getMisProductos(id:String){
        viewModelScope.launch {
            isLoading.value=true
            if( productoService.getProductos() !=null){
                misProductos.value= productoService.getProductos()!!.filter {producto->
                    producto.vendidoPor==id
                }
            }
            Log.i("home",misProductos.value.toString())
            isLoading.value=false
        }
    }

    fun comprarProducto(producto: Producto){
        viewModelScope.launch {
            val sp = MainApplication.applicationContext().getSharedPreferences(
                "preferences",
                Context.MODE_PRIVATE
            )

            productoService.comprarProducto(
                Producto(
                    producto.id,
                    producto.titulo,
                    producto.descripcion,
                    producto.precio,
                    producto.estado,
                    sp.getString("LOGGED_ID",""),
                    producto.vendidoPor
                )
            )
        }
    }

    fun borrarProducto(producto:Producto){
        viewModelScope.launch {
            productoService.borrarProducto(producto)
            getProductos()
        }
    }

    fun getVendedor(id:String){
        viewModelScope.launch {
            if(usuarioService.getVendedor(id)!=null){
                loadingVendedor.value=true
                if(usuarioService.getVendedor(id)==null){
                    vendedorEmpty.value=true
                }else{
                    vendedorProducto.value=usuarioService.getVendedor(id)!!
                }
                loadingVendedor.value=false
            }
        }
    }
}