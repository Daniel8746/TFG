#!/bin/bash

# Pedir al usuario que ingrese el mensaje del commit
echo "Introduce el mensaje del commit:"
read commit_message

# Agregar todos los cambios (modificados, nuevos archivos, eliminados)
git add .

# Confirmar los cambios con el mensaje proporcionado por el usuario
git commit -m "$commit_message"

# Subir los cambios al repositorio remoto en GitHub
git push origin main

echo "Cambios subidos exitosamente a GitHub"