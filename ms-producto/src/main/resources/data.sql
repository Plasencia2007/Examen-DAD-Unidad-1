-- Categorías
MERGE INTO categorias dst
  USING (SELECT 'Electrónica' AS nombre, 'Productos electrónicos y gadgets' AS descripcion, 1 AS activo FROM DUAL) src
  ON (UPPER(dst.nombre) = UPPER(src.nombre))
  WHEN NOT MATCHED THEN INSERT (nombre, descripcion, activo) VALUES (src.nombre, src.descripcion, src.activo);

MERGE INTO categorias dst
  USING (SELECT 'Ropa' AS nombre, 'Prendas de vestir' AS descripcion, 1 AS activo FROM DUAL) src
  ON (UPPER(dst.nombre) = UPPER(src.nombre))
  WHEN NOT MATCHED THEN INSERT (nombre, descripcion, activo) VALUES (src.nombre, src.descripcion, src.activo);

MERGE INTO categorias dst
  USING (SELECT 'Alimentos' AS nombre, 'Comida y bebidas' AS descripcion, 1 AS activo FROM DUAL) src
  ON (UPPER(dst.nombre) = UPPER(src.nombre))
  WHEN NOT MATCHED THEN INSERT (nombre, descripcion, activo) VALUES (src.nombre, src.descripcion, src.activo);

-- Productos
MERGE INTO productos dst
  USING (SELECT 'Laptop HP' AS nombre, 'Laptop HP Pavilion 15"' AS descripcion, 2499.99 AS precio, 10 AS stock, 1 AS activo,
                (SELECT id FROM categorias WHERE UPPER(nombre) = UPPER('Electrónica')) AS categoria_id FROM DUAL) src
  ON (UPPER(dst.nombre) = UPPER(src.nombre) AND dst.categoria_id = src.categoria_id)
  WHEN NOT MATCHED THEN INSERT (nombre, descripcion, precio, stock, activo, categoria_id)
    VALUES (src.nombre, src.descripcion, src.precio, src.stock, src.activo, src.categoria_id);

MERGE INTO productos dst
  USING (SELECT 'Mouse Logitech' AS nombre, 'Mouse inalámbrico' AS descripcion, 89.90 AS precio, 50 AS stock, 1 AS activo,
                (SELECT id FROM categorias WHERE UPPER(nombre) = UPPER('Electrónica')) AS categoria_id FROM DUAL) src
  ON (UPPER(dst.nombre) = UPPER(src.nombre) AND dst.categoria_id = src.categoria_id)
  WHEN NOT MATCHED THEN INSERT (nombre, descripcion, precio, stock, activo, categoria_id)
    VALUES (src.nombre, src.descripcion, src.precio, src.stock, src.activo, src.categoria_id);

MERGE INTO productos dst
  USING (SELECT 'Polo Nike' AS nombre, 'Polo deportivo talla M' AS descripcion, 79.00 AS precio, 30 AS stock, 1 AS activo,
                (SELECT id FROM categorias WHERE UPPER(nombre) = UPPER('Ropa')) AS categoria_id FROM DUAL) src
  ON (UPPER(dst.nombre) = UPPER(src.nombre) AND dst.categoria_id = src.categoria_id)
  WHEN NOT MATCHED THEN INSERT (nombre, descripcion, precio, stock, activo, categoria_id)
    VALUES (src.nombre, src.descripcion, src.precio, src.stock, src.activo, src.categoria_id);

MERGE INTO productos dst
  USING (SELECT 'Arroz Costeño' AS nombre, 'Saco 5kg' AS descripcion, 28.50 AS precio, 100 AS stock, 1 AS activo,
                (SELECT id FROM categorias WHERE UPPER(nombre) = UPPER('Alimentos')) AS categoria_id FROM DUAL) src
  ON (UPPER(dst.nombre) = UPPER(src.nombre) AND dst.categoria_id = src.categoria_id)
  WHEN NOT MATCHED THEN INSERT (nombre, descripcion, precio, stock, activo, categoria_id)
    VALUES (src.nombre, src.descripcion, src.precio, src.stock, src.activo, src.categoria_id);
