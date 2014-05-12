package fr.paris.lutece.plugins.stock.modules.tickets.business;

import fr.paris.lutece.plugins.stock.business.product.IProductDAO;
import fr.paris.lutece.plugins.stock.business.product.Product;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;

import java.util.List;


/**
 * The Interface for show dao.
 */
public interface IShowDAO extends IProductDAO
{

    List<Product> getCurrentProduct( List<String> orderList, PaginationProperties paginator );

    /**
     * Gets the come product.
     * 
     * @param orderList the order list
     * @param paginator the paginator
     * @return the come product
     */
    List<Product> getComeProduct( List<String> orderList, PaginationProperties paginator );

    /**
     * Return a list of product with date_end after the current date.
     * 
     * @param orderList the order list
     * @return list of product
     */
    List<Product> getCurrentAndComeProduct( List<String> orderList );

}