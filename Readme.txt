UO264476	Aitor Llanos Irazola	1
Se ha tenido en cuenta el correo de Lourdes el dia 27 de octubre de 2020, 10:06
Donde explica sobre las excepciones:
Buenos días,

  1) Muchos errores ya están contemplados en la interfaz de servicio y deben lanzar BusinessException.

  2) Los demás lanzarán IllegalArgumentException y rompen la ejecución. Con ello, el programador debería darse cuenta de que hay algunas comprobaciones en el código que deberían haberse hecho y no se hicieron.

En nuestro caso, si al objeto de negocio le llega un dni null, por ejemplo, es porque no se hizo correctamente la entrada de datos en la capa UI. Pero no es algo que vosotros debáis modificar en esta ocasión.

  3) Aseguraos de comprobar todas las posibles fuentes de BusinessException. Porque nosotros sí lo vamos a hacer.

Y aunque en el enunciado decia: 
No es válido que la aplicación termine de forma abrupta con una traza
de excepción en pantalla.

Se hace que pueda terminar con una traza de excepcion con illegalArgumentException para aquellos errores que no estaban contemplados en el service debido, tal y como explica lourdes en el correo.