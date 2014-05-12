/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.stock.modules.tickets.service;

import fr.paris.lutece.plugins.stock.business.category.Category;
import fr.paris.lutece.plugins.stock.business.category.CategoryFilter;
import fr.paris.lutece.plugins.stock.business.category.ICategoryDAO;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.commons.exception.BusinessException;
import fr.paris.lutece.plugins.stock.commons.exception.ValidationException;
import fr.paris.lutece.plugins.stock.utils.StockUtils;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;


/**
 * CategoryService.
 */
@Transactional
public final class CategoryService implements ICategoryService
{
    // private static final String CATEGORY_NAME_KEY =
    // "stock.create_category.type.basic.name";
    /** The Constant MESSAGE_ERROR_CATEGORY_NAME_MUST_BE_UNIQUE. */
    private static final String MESSAGE_ERROR_CATEGORY_NAME_MUST_BE_UNIQUE = "module.stock.billetterie.save_category.error.name.unique";

    /** The _instance. */
    private static CategoryService _instance = new CategoryService(  );

    /** The _dao category. */
    @Inject
    private ICategoryDAO _daoCategory;

    /**
     * Private constructor.
     */
    private CategoryService(  )
    {
    }

    /**
     * Get an instance of {@link CategoryService}.
     *
     * @return An instance of {@link CategoryService}
     */
    public static CategoryService getInstance(  )
    {
        return _instance;
    }

    /**
     * {@inheritDoc}
     */
    public void init(  )
    {
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public String doCreateCategory( Category category )
    {
        _daoCategory.create( category );

        Category parent = category.getParent(  );

        if ( parent != null )
        {
            Category trueParent = _daoCategory.findById( parent.getId(  ) );

            if ( trueParent != null )
            {
                trueParent.getChildrenList(  ).add( category );
                _daoCategory.update( trueParent );
            }
            else
            {
                return StockUtils.MESSAGE_ERROR_OCCUR;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public String doModifyCategory( Category category )
    {
        _daoCategory.update( category );

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void doDeleteCategory( int nIdCategory )
    {
        // Remove the category
        _daoCategory.remove( nIdCategory );
    }

    /**
     * {@inheritDoc}
     */
    public Category getCategory( int nIdCategory )
    {
        return _daoCategory.findById( nIdCategory );
    }

    /**
     *
     * {@inheritDoc}
     */
    public Category getCategoryWithParent( int nIdCategory )
    {
        return _daoCategory.findByIdWithParent( nIdCategory );
    }

    /**
     * {@inheritDoc}
     */
    public Category getCategoryWithChildren( Integer idCategory )
    {
        return _daoCategory.findByIdWithChildren( idCategory );
    }

    /**
     *
     * {@inheritDoc}
     */
    public List<Category> findAllFirstLevelWithChildren(  )
    {
        return _daoCategory.selectAllFirstLevelWithChildrenWithProduct(  );
    }

    /**
     *
     * {@inheritDoc}
     */
    public List<Category> findAllChildrenWithChildren( Integer idCategory )
    {
        return _daoCategory.selectAllChildrenWithChildrenWithProduct( idCategory );
    }

    /**
     *
     * {@inheritDoc}
     */
    public Category findByPrimarykeyWithChildren( Integer nIdCategory )
    {
        return _daoCategory.findByIdWithChildren( nIdCategory );
    }

    /**
     *
     * {@inheritDoc}
     */
    public Category findByIdWithParent( Integer nIdCategory )
    {
        return _daoCategory.findByIdWithParent( nIdCategory );
    }

    /**
     *
     * {@inheritDoc}
     */
    public Category findByIdWithProduct( Integer nIdCategory )
    {
        return _daoCategory.findByIdWithProduct( nIdCategory );
    }

    /**
     * Finds by filter and fetch children.
     *
     * @param filter the filter
     * @return the category list
     */
    public List<Category> findByFilterWithChildren( CategoryFilter filter )
    {
        return _daoCategory.findByFilterWithChildren( filter );
    }

    /**
     * Finds by filter.
     *
     * @param filter the filter
     * @param paginationProperties the pagination properties
     * @return the category list
     */
    public ResultList<Category> findByFilter( CategoryFilter filter, PaginationProperties paginationProperties )
    {
        return _daoCategory.findByFilter( filter, paginationProperties );
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.paris.lutece.plugins.stock.modules.tickets.service.ICategoryService
     * #findAll()
     */

    /**
     * {@inheritDoc}
     */
    public List<Category> findAll(  )
    {
        return _daoCategory.findAll(  );
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.paris.lutece.plugins.stock.modules.tickets.service.ICategoryService
     * #findById(java.lang.Integer)
     */

    /**
     * {@inheritDoc}
     */
    public Category findById( Integer nIdCategory )
    {
        return _daoCategory.findById( nIdCategory );
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.paris.lutece.plugins.stock.modules.tickets.service.ICategoryService
     * #update(fr.paris.lutece.plugins.stock.business.category.Category)
     */

    /**
     * {@inheritDoc}
     */
    public void update( Category category )
    {
        _daoCategory.update( category );
    }

    /**
     * {@inheritDoc}
     * @throws ValidationException
     */
    public void doSaveCategory( Category category ) throws ValidationException
    {
        List<Category> listeCategory = _daoCategory.getAllByName( category.getName(  ) );

        if ( ( category.getId(  ) != null ) && ( category.getId(  ) > 0 ) )
        {
            // Update
            if ( ( listeCategory != null ) &&
                    ( ( listeCategory.size(  ) > 1 ) ||
                    ( ( listeCategory.size(  ) == 1 ) &&
                    !listeCategory.get( 0 ).getId(  ).equals( category.getId(  ) ) ) ) )
            {
                throw new BusinessException( category, MESSAGE_ERROR_CATEGORY_NAME_MUST_BE_UNIQUE );
            }

            _daoCategory.update( category );
        }
        else
        {
            // Create
            if ( ( listeCategory != null ) && ( listeCategory.size(  ) > 0 ) )
            {
                throw new BusinessException( category, MESSAGE_ERROR_CATEGORY_NAME_MUST_BE_UNIQUE );
            }

            _daoCategory.create( category );
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Category> findAll( List<String> orderList )
    {
        return _daoCategory.findAll( orderList );
    }
}
