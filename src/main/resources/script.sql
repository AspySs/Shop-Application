CREATE TABLE EXPENSE_ITEMS
(
    Id   integer PRIMARY KEY NOT NULL,
    Name VARCHAR(100)
);

CREATE TABLE WAREHOUSES
(
    Id       integer PRIMARY KEY NOT NULL,
    Name     VARCHAR(50),
    Quantity NUMERIC,
    Amount   NUMERIC(18, 2)
);

CREATE TABLE CHARGES
(
    Id              integer PRIMARY KEY NOT NULL,
    Amount          NUMERIC(18, 2),
    Charge_Date     DATE,
    Expanse_Item_Id integer,
    CONSTRAINT fk_charges_expense_items foreign key (Expanse_Item_Id) REFERENCES EXPENSE_ITEMS (id)
);

CREATE TABLE SALES
(
    Id           integer PRIMARY KEY NOT NULL,
    Quantity     NUMERIC,
    Amount       NUMERIC(18, 2),
    Sale_Date    Date,
    Warehouse_Id integer,
    CONSTRAINT fk_sales_warehouses foreign key (Warehouse_Id) REFERENCES WAREHOUSES (id)
);