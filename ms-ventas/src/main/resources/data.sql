-- Venta 1 (confirmada, cliente 1 compra productos 1 y 2)
INSERT INTO ventas (id, fecha, cliente_id, total, estado)
VALUES (1, '2026-04-10 10:00:00', 1, 2589.89, 'CONFIRMADA')
ON CONFLICT (id) DO NOTHING;

INSERT INTO detalles (id, venta_id, producto_id, cantidad, precio_unitario, subtotal)
VALUES (1, 1, 1, 1, 2499.99, 2499.99)
ON CONFLICT (id) DO NOTHING;

INSERT INTO detalles (id, venta_id, producto_id, cantidad, precio_unitario, subtotal)
VALUES (2, 1, 2, 1, 89.90, 89.90)
ON CONFLICT (id) DO NOTHING;

-- Venta 2 (pendiente, cliente 2 compra producto 3)
INSERT INTO ventas (id, fecha, cliente_id, total, estado)
VALUES (2, '2026-04-15 15:30:00', 2, 158.00, 'PENDIENTE')
ON CONFLICT (id) DO NOTHING;

INSERT INTO detalles (id, venta_id, producto_id, cantidad, precio_unitario, subtotal)
VALUES (3, 2, 3, 2, 79.00, 158.00)
ON CONFLICT (id) DO NOTHING;

-- Resetear secuencias para que los nuevos registros no colisionen con los semilla
SELECT setval('ventas_id_seq', GREATEST((SELECT MAX(id) FROM ventas), 1));
SELECT setval('detalles_id_seq', GREATEST((SELECT MAX(id) FROM detalles), 1));
