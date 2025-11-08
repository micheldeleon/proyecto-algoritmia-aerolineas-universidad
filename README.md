ORT 2024
La ORT pidió desarrollar un proyecto de algoritmia y estructuras de datos II aplicando los conceptos vistos en clase, principalmente árboles binarios de búsqueda y árboles balanceados, dentro de un sistema práctico. El objetivo general era que el estudiante implementara un sistema para una aerolínea, donde cada funcionalidad debía justificarse desde el punto de vista algorítmico y de complejidad temporal.


1. Registrar Jugador 

En esta funcionalidad, estamos registrando nuevos jugadores en un 
sistema de gestión. Utilizamos árboles de búsqueda, que son como listas 
organizadas de manera especial. Cuando agregamos un nuevo elemento, el 
sistema busca dónde colocarlo en estos árboles. En cada paso de árbol de 
búsqueda, eliminan la mitad de las opciones. Por eso, incluso si tenemos un 
montón de jugadores, el tiempo que tardamos en encontrar el lugar correcto 
para uno nuevo es muy corto. Esto significa que, aunque nuestro sistema 
crezca mucho, el tiempo que tardamos en agregar jugadores no se volverá 
demasiado largo. 

2. Buscar Jugador 

En esta funcionalidad, estamos tratando de encontrar a un jugador en 
nuestro sistema usando su alias. Primero, nos aseguramos de que su alias 
proporcionado sea válido y esté en el formato correcto. Luego buscamos al 
jugador en nuestra lista. Almacenamos a los jugadores en una especie de lista 
llamada árbol binario de búsqueda. Este árbol está organizado de una manera 
que hace que la búsqueda sea rápida. Cuando buscamos a un jugador en el 
árbol, cada vez que hacemos una comparación, eliminamos la mitad de los 
jugadores que necesitamos revisar. El tiempo que tardamos en encontrar al 
jugador aumenta muy poco incluso si agregamos muchos más jugadores al 
sistema. 

3. Listar jugadores por alias ascendente 
 
En esta funcionalidad, estamos recorriendo el árbol de jugadores para 
imprimir los datos en orden ascendente. Este recorrido implica visitar cada 
nodo una vez. Entonces, si hay 'n' jugadores en el árbol, tendremos que visitar 'n' 
nodos. Por eso, el tiempo que tardamos en imprimir a todos los jugadores
aumenta en línea recta con el número de jugadores. Si hay más jugadores, 
tomará más tiempo imprimirlos a todos en orden ascendente. 


4. Listar jugadores por categoría 
 
En esta funcionalidad, estamos buscando y listando a los jugadores que 
pertenecen a una categoría específica. Recorrimos el árbol de jugadores para 
encontrar solo los jugadores de esa categoría en particular. La cantidad de 
jugadores que pertenecen a esa categoría la llamamos 'k'. Entonces, si hay 'k' 
jugadores en esa categoría, el tiempo que nos llevará listarlos será proporcional 
a 'k'. No tenemos que revisar todos los jugadores en el sistema, solo aquellos 
que pertenecen a la categoría que estamos buscando. 

5. Registrar Equipo 

En esta funcionalidad, estamos buscando asegurarnos de que agregar 
un nuevo equipo sea rápido y eficiente, incluso si ya hay muchos equipos en el 
sistema. Estamos utilizando un árbol de búsqueda binaria balanceado. Cada 
vez que agregamos un equipo el árbol se organiza automáticamente para 
mantener el equilibrio. Esto significa que, incluso si hay muchos equipos 
registrados, el tiempo necesario para agregar una nueva será rápido y eficiente. 
Cuando buscamos si ya hemos alcanzado la cantidad máxima de equipos
permitidas, esta verificación también se hace de manera efectivo. Como 
estamos usando un árbol de búsqueda binaria balanceado, la operación para 
obtener la cantidad de equipos registradas en el sistema tiene un tiempo de 
ejecución de O(log n), donde 'n' es el número total de equipos. Esto significa 
que, incluso si hay muchos equipos, el tiempo necesario para verificar si hemos 
alcanzado el límite máximo será rápido. 

6. Agregar jugador a equipo 

En funcionalidad tiene una complejidad de tiempo de O(log n) + O(log m), 
donde "n" es el número total de equipos y “m” es el número total de jugadores 
en el equipo. Esto significa que el tiempo que tarda en ejecutarse aumenta de 
manera lineal con la cantidad de equipos y jugadores. Para agregarse un 
jugador a un equipo primero realizamos la búsqueda del correspondiente, una 
vez obtenido seleccionamos su lista de jugadores y llamamos al método 
agregar para que se ejecute.

7. Listar jugadores de equipo 

En funcionalidad tiene una complejidad de tiempo de O(log n), luego 
de hacer todas las validaciones realizamos la búsqueda del equipo, al 
encontrar al equipo seleccionamos el árbol de jugadores y llamamos a su 
método de impresión.

8. Listar equipos por orden descendente 

En funcionalidad tiene una complejidad de tiempo de O(n),
teniendo la lista de equipos llamamos un método de impresión que 
ordena la lista en el orden correspondiente. En ese método usamos el 
“append” que tiene 0(1), por lo tanto, se cumpliría el orden que el ejercicio 
pide.
