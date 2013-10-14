--
-- core_feature_group
--
INSERT INTO core_feature_group(id_feature_group, feature_group_description, feature_group_label, feature_group_order)
	VALUES ('KIOSQUE', 'module.stock.kiosque.features.group.kiosque.description', 'module.stock.kiosque.features.group.kiosque.label', 8);

--	
-- core_admin_right
--
INSERT INTO core_admin_right(id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url,id_order)
VALUES 
('STATISTICS_MANAGEMENT', 'module.stock.kiosque.adminFeature.statisticsManagement.name', 2, 'jsp/admin/plugins/stock/modules/kiosque/ManageStatistics.jsp', 'module.stock.kiosque.adminFeature.statisticsManagement.description', 0, 'stock-kiosque', 'KIOSQUE', 'images/admin/skin/plugins/stock/modules/kiosque/statistics.png', '', 4),
('PARTNERS_MANAGEMENT', 'module.stock.kiosque.adminFeature.partnersManagement.name', 2, 'jsp/admin/plugins/stock/modules/kiosque/ManagePartners.jsp', 'module.stock.kiosque.adminFeature.partnersManagement.description', 0, 'stock-kiosque', 'KIOSQUE', 'images/admin/skin/plugins/stock/modules/kiosque/partner.png', '', 3),
('OFFERS_MANAGEMENT', 'module.stock.kiosque.adminFeature.offresManagement.name', 2, 'jsp/admin/plugins/stock/modules/kiosque/ManageOffers.jsp', 'module.stock.kiosque.adminFeature.offresManagement.description', 0, 'stock-kiosque', 'KIOSQUE', 'images/admin/skin/plugins/stock/modules/kiosque/ticket.png', '', 2);