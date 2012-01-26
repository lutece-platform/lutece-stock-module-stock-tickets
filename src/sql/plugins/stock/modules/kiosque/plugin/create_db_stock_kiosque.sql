/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

--
-- Definition of table kiosque_history
--

DROP TABLE IF EXISTS kiosque_history;
CREATE TABLE kiosque_history (
  id_history int default '0' NOT NULL,
  date_operation datetime default NULL,
  date_session datetime default NULL,
  event varchar(255) collate utf8_unicode_ci default NULL,
  hour_session datetime default NULL,
  id_offer varchar(255) collate utf8_unicode_ci default NULL,
  id_session int default NULL,
  kiosque varchar(255) collate utf8_unicode_ci default NULL,
  partner varchar(255) collate utf8_unicode_ci default NULL,
  partner_nickname varchar(255) collate utf8_unicode_ci default NULL,
  quantity int default NULL,
  type varchar(255) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (id_history)
);

--
-- Definition of table kiosque_offer
--

DROP TABLE IF EXISTS kiosque_offer;
CREATE TABLE kiosque_offer (
  commentary varchar(255) collate utf8_unicode_ci default NULL,
  id varchar(255) collate utf8_unicode_ci default NULL,
  nbPerson int default '0' NOT NULL,
  reducedPrice float default '0' NOT NULL,
  type varchar(255) collate utf8_unicode_ci default NULL,
  id_category int default '0' NOT NULL,
  genre_id_offer_genre int default NULL,
  bookingFax varchar(255) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (id_category),
  KEY FK98243DDC5EF75F6 (genre_id_offer_genre),
  KEY FK98243DDCD4687647 (id_category),
  CONSTRAINT FK98243DDCD4687647 FOREIGN KEY (id_category) REFERENCES stock_ticket_category (id_category),
  CONSTRAINT FK98243DDC5EF75F6 FOREIGN KEY (genre_id_offer_genre) REFERENCES kiosque_offer_genre (id_offer_genre)
);

--
-- Definition of table kiosque_offer_genre
--

DROP TABLE IF EXISTS kiosque_offer_genre;
CREATE TABLE kiosque_offer_genre (
  id_offer_genre int default '0' NOT NULL,
  name varchar(255) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (id_offer_genre)
);

--
-- Definition of table kiosque_partner
--

DROP TABLE IF EXISTS kiosque_partner;
CREATE TABLE kiosque_partner (
  booking_mail varchar(255) collate utf8_unicode_ci default NULL,
  booking_phone varchar(255) collate utf8_unicode_ci default NULL,
  nickname varchar(255) collate utf8_unicode_ci default NULL,
  schedule varchar(255) collate utf8_unicode_ci default NULL,
  id_provider int default '0' NOT NULL,
  type_id int default NULL,
  PRIMARY KEY  (id_provider),
  KEY FK4D1A204836F73D41 (id_provider),
  KEY FK4D1A2048D88B6E77 (type_id),
  CONSTRAINT FK4D1A2048D88B6E77 FOREIGN KEY (type_id) REFERENCES kiosque_type_partner (id_type_partner),
  CONSTRAINT FK4D1A204836F73D41 FOREIGN KEY (id_provider) REFERENCES stock_provider (id_provider)
);

--
-- Definition of table kiosque_session
--

DROP TABLE IF EXISTS kiosque_session;
CREATE TABLE kiosque_session (
  id_product int default '0' NOT NULL,
  parent_id  int default NULL,
  initial_quantity int default NULL,
  PRIMARY KEY  (id_product),
  KEY FKF2ADC6161B926833 (id_product),
  KEY FKF2ADC616ED70CFC9 (parent_id),
  CONSTRAINT FKF2ADC616ED70CFC9 FOREIGN KEY (parent_id) REFERENCES kiosque_session (id_product),
  CONSTRAINT FKF2ADC6161B926833 FOREIGN KEY (id_product) REFERENCES stock_ticket_product (id_product)
);

--
-- Definition of table kiosque_survey
--

DROP TABLE IF EXISTS kiosque_survey;
CREATE TABLE kiosque_survey (
  idFormSubmit int default '0' NOT NULL,
  idKiosque int default '0' NOT NULL,
  PRIMARY KEY  (idFormSubmit,idKiosque)
);

--
-- Definition of table kiosque_survey_export_format
--

DROP TABLE IF EXISTS kiosque_survey_export_format;
CREATE TABLE kiosque_survey_export_format (
  id_export int default '0' NOT NULL,
  description varchar(255) collate utf8_unicode_ci default NULL,
  extension varchar(255) collate utf8_unicode_ci default NULL,
  title varchar(255) collate utf8_unicode_ci default NULL,
  xsl_file longblob,
  PRIMARY KEY  (id_export)
);

--
-- Definition of table kiosque_type_partner
--

DROP TABLE IF EXISTS kiosque_type_partner;
CREATE TABLE kiosque_type_partner (
  id_type_partner int default '0' NOT NULL,
  name varchar(255) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (id_type_partner)
);

--
-- Definition of table kiosque_visitor
--

DROP TABLE IF EXISTS kiosque_visitor;
CREATE TABLE kiosque_visitor (
  date datetime NOT NULL default '0000-00-00 00:00:00',
  id_user int default '0' NOT NULL,
  nb_visitors int default NULL,
  PRIMARY KEY  (date,id_user)
);

--
-- Definition of table kiosque_session_kiosque_session
--
DROP TABLE IF EXISTS kiosque_session_kiosque_session;
CREATE TABLE  kiosque_session_kiosque_session (
  kiosque_session_id_product int default '0' NOT NULL,
  subSessions_id_product int default '0' NOT NULL,
  UNIQUE KEY subSessions_id_product (subSessions_id_product),
  KEY FKF0C9F5ED63005F66 (subSessions_id_product),
  KEY FKF0C9F5EDE83F5A8D (kiosque_session_id_product),
  CONSTRAINT FKF0C9F5EDE83F5A8D FOREIGN KEY (kiosque_session_id_product) REFERENCES kiosque_session (id_product),
  CONSTRAINT FKF0C9F5ED63005F66 FOREIGN KEY (subSessions_id_product) REFERENCES kiosque_session (id_product)
);

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;