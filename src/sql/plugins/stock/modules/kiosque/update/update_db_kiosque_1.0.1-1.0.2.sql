-- 
-- add column parent_id for table kiosque_session
--
ALTER TABLE kiosque_session ADD parent_id int default NULL;

-- 
-- add column initial_quantity for table kiosque_session
--
ALTER TABLE kiosque_session ADD initial_quantity int default NULL;

--
-- create the table kiosque_session_kiosque_session
--
CREATE TABLE  kiosque_session_kiosque_session (
  kiosque_session_id_product int default '0' NOT NULL,
  subSessions_id_product int default '0' NOT NULL,
  UNIQUE KEY subSessions_id_product (subSessions_id_product),
  KEY FKF0C9F5ED63005F66 (subSessions_id_product),
  KEY FKF0C9F5EDE83F5A8D (kiosque_session_id_product),
  CONSTRAINT FKF0C9F5EDE83F5A8D FOREIGN KEY (kiosque_session_id_product) REFERENCES kiosque_session (id_product),
  CONSTRAINT FKF0C9F5ED63005F66 FOREIGN KEY (subSessions_id_product) REFERENCES kiosque_session (id_product)
);