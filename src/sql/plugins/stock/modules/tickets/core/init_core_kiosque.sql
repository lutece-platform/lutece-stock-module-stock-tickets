--
-- core_feature_group
--
INSERT INTO core_feature_group(id_feature_group, feature_group_description, feature_group_label, feature_group_order)
	VALUES ('TICKETS', 'module.stock.tickets.features.group.tickets.description', 'module.stock.tickets.features.group.tickets.label', 8);

--	
-- core_admin_right
--
INSERT INTO core_admin_right(id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url,id_order)
VALUES 
('STATISTICS_MANAGEMENT', 'module.stock.tickets.adminFeature.statisticsManagement.name', 2, 'jsp/admin/plugins/stock/modules/tickets/ManageStatistics.jsp', 'module.stock.tickets.adminFeature.statisticsManagement.description', 0, 'stock-tickets', 'TICKETS', 'images/admin/skin/plugins/stock/modules/tickets/statistics.png', '', 4),
('PARTNERS_MANAGEMENT', 'module.stock.tickets.adminFeature.partnersManagement.name', 2, 'jsp/admin/plugins/stock/modules/tickets/ManagePartners.jsp', 'module.stock.tickets.adminFeature.partnersManagement.description', 0, 'stock-tickets', 'TICKETS', 'images/admin/skin/plugins/stock/modules/tickets/partner.png', '', 3),
('OFFERS_MANAGEMENT', 'module.stock.tickets.adminFeature.offresManagement.name', 2, 'jsp/admin/plugins/stock/modules/tickets/ManageOffers.jsp', 'module.stock.tickets.adminFeature.offresManagement.description', 0, 'stock-tickets', 'TICKETS', 'images/admin/skin/plugins/stock/modules/tickets/ticket.png', '', 2);

-- Types de representations
INSERT INTO `stock_offer_genre` (`id_offer_genre`, `name`) VALUES
(1, 'Tarif réduit'),
(2, 'Invitation'),
(3, 'Invitation spectacle enfants');
