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
import fr.paris.lutece.plugins.stock.modules.tickets.business.ShowCategoryDTO;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * Class ShowCategoryService.
 * 
 */
@Transactional
public final class ShowCategoryService implements IShowCategoryService
{
    private static final String MESSAGE_ERROR_CATEGORY_NAME_MUST_BE_UNIQUE = "module.stock.billetterie.save_category.error.name.unique";

    /** The _instance. */
    private static IShowCategoryService _instance = new ShowCategoryService( );

    /** The _dao category. */
    @Inject
    private ICategoryDAO _daoCategory;

    /**
     * Private constructor.
     */
    private ShowCategoryService( )
    {
    }

    /**
     * Get the instance of the service.
     * 
     * @return The unique instance of the service
     */
    public static IShowCategoryService getInstance( )
    {
        return _instance;
    }

    /**
     * {@inheritDoc}
     */
    public void init( )
    {
    }

    /**
     * {@inheritDoc}
     */
    public ResultList<ShowCategoryDTO> findByFilter( CategoryFilter filter, PaginationProperties paginationProperties )
    {
        return ShowCategoryDTO.convertEntityList( _daoCategory.findByFilter( filter, paginationProperties ) );
    }

    /**
     * {@inheritDoc}
     */
    public ShowCategoryDTO findById( Integer nIdCategory )
    {
        Category entity = _daoCategory.findById( nIdCategory );
        ShowCategoryDTO foundCategory = null;
        if ( entity != null )
        {
            foundCategory = ShowCategoryDTO.convertEntity( _daoCategory.findById( nIdCategory ) );
        }

        return foundCategory;
    }

    /**
     * {@inheritDoc}
     */
    public void doSaveCategory( ShowCategoryDTO category ) throws ValidationException
    {
        List<ShowCategoryDTO> listeCategory = ShowCategoryDTO.convertEntityList( _daoCategory.getAllByName( category
                .getName( ) ) );

        if ( category.getId( ) != null && category.getId( ) > 0 )
        {
            // Update
            if ( listeCategory != null
                    && ( listeCategory.size( ) > 1 || listeCategory.size( ) == 1
                            && !listeCategory.get( 0 ).getId( ).equals( category.getId( ) ) ) )
            {
                throw new BusinessException( category, MESSAGE_ERROR_CATEGORY_NAME_MUST_BE_UNIQUE );
            }

            _daoCategory.update( category.convert( ) );
        }
        else
        {
            // Create
            if ( !listeCategory.isEmpty( ) )
            {
                throw new BusinessException( category, MESSAGE_ERROR_CATEGORY_NAME_MUST_BE_UNIQUE );
            }
            _daoCategory.create( category.convert( ) );
        }

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

}
