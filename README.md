*This repository has a practice for the University of Oviedo.

- Subject: Repositorios de Informacion.
- Practice: 1, JDBC.
- Year: September-October, 2020.

*This repository is intended to be in english, but it can have some words in spanish, this should be changed.


*If you want to use it, feel free, but always under your responsability.


- Mark:6
- Correction from the teacher: 
EJECUCIÓN

Listar pedidos y ver detalle no debería lanzar BE. Ver detalle devuelve valores incorrectos.

No funciona eliminar repuesto porque te confundiste al comprobar si forma parte de proveedores. No entiendo porque cuando actualizas el precio de un repuesto también lo haces sobre el proveedor y la workorder. Te has hecho un lio tremendo.

No funciona generar pedidos cuando dos proveedore con el mismo precio y diferente tiempo de entrega.

DISEÑO

No entregas modelo de dominio

Los TDG no se identifican con la tabla: linkWorkordersToInvoice, markWorkOrderAsInvoiced
