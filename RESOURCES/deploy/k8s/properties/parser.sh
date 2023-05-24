#!/bin/bash

# Directorio donde se encuentran los archivos .properties
DIRECTORY="."

# Nombre del ConfigMap
CONFIGMAP_NAME="properties-configmap"

# Comienza el ConfigMap
echo "apiVersion: v1"
echo "kind: ConfigMap"
echo "metadata:"
echo "  name: $CONFIGMAP_NAME"
echo "data:"

# Función para agregar el contenido de un archivo .properties al ConfigMap
add_to_configmap() {
    filename=$(basename -- "$1")
    echo "  $filename: |"
    while IFS='=' read -r key value
    do
        # Comprobar si la clave y el valor existen antes de añadirlos
        if [[ -n $key && -n $value ]]; then
            echo "    $key: $value"
        fi
    done < "$1"
}

# Para cada archivo .properties en el directorio, añadir su contenido al ConfigMap
for file in "$DIRECTORY"/*.properties
do
    add_to_configmap "$file"
done