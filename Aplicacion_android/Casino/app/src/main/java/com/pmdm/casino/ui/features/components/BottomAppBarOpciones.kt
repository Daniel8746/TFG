package com.pmdm.casino.ui.features.components

/*@Immutable
data class OpcionAppBar(
    val descripcion: String,
    val activo : Boolean = true,
    val icono1: Int,
    val icono2: Int = -1,
    var onClick: () -> Unit,
)

@Composable
fun BottomAppBarOpciones(
    opciones: List<OpcionAppBar>,
    onNavegarEditar: () -> Unit
) {
    BottomAppBar(
        actions = {
            opciones.forEach { o ->
                OutlinedIconButton(
                    onClick = o.onClick,
                    enabled = o.activo
                ) {
                    Row {
                        Icon(
                            painter = painterResource(o.icono1),
                            contentDescription = o.descripcion
                        )
                        if (o.icono2 != -1) {
                            Icon(
                                painter = painterResource(o.icono2),
                                contentDescription = o.descripcion
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavegarEditar,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                contentColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(painter = painterResource(R.drawable.edit_24px), "Editar Fármaco")
            }
        }
    )
}
*/