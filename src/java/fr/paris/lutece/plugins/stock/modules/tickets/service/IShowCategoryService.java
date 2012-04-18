package fr.paris.lutece.plugins.stock.modules.tickets.service;

import fr.paris.lutece.plugins.stock.business.category.CategoryFilter;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.commons.exception.ValidationException;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ShowCategoryDTO;

import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * interface IShowCategoryService
 * 
 */
public interface IShowCategoryService
{

    /**
     * Init the object.
     */
    void init( );

    /**
     * Finds by filter.
     * 
     * @param filter the filter
     * @param paginationProperties the pagination properties
     * @return the category list
     */
    ResultList<ShowCategoryDTO> findByFilter( CategoryFilter filter,
            PaginationProperties paginationProperties );

    /**
     * Find by id.
     * 
     * @param nIdCategory the id to look for
     * @return the found ShowCategoryDTO, <code>null</code> otherwise
     */
    ShowCategoryDTO findById( Integer nIdCategory );

    /**
     * Modify a category.
     * 
     * @param category the category to save
     * @throws ValidationException if category is inconsistent
     */
    void doSaveCategory( ShowCategoryDTO category ) throws ValidationException;

    /**
     * {@inheritDoc}
     */
    @Transactional
    void doDeleteCategory( int nIdCategory );

}