/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import fr.paris.lutece.plugins.stock.business.product.IProductImageDAO;
import fr.paris.lutece.plugins.stock.business.product.Product;
import fr.paris.lutece.plugins.stock.business.product.ProductFilter;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.commons.exception.BusinessException;
import fr.paris.lutece.plugins.stock.commons.exception.ValidationException;
import fr.paris.lutece.plugins.stock.modules.tickets.business.IShowDAO;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ShowDTO;
import fr.paris.lutece.plugins.stock.service.ProductService;
import fr.paris.lutece.plugins.stock.utils.DateUtils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * ProductService
 *
 */
public class ShowService extends ProductService implements IShowService
{
    public static final String ID_SPRING_DEFAULT = "stock-tickets.showService";
    private static final String MESSAGE_ERROR_PRODUCT_NAME_MUST_BE_UNIQUE = "module.stock.billetterie.save_product.error.name.unique";
    private static final String MESSAGE_ERROR_PRODUCT_DATE_CHEVAUCHE = "module.stock.billetterie.save_product.error.date.chevauche";

    @Inject
    @Named( "stock-tickets.showDAO" )
	private IShowDAO _daoProduct;

    @Inject
    private IProductImageDAO _daoProductImage;

    /**
     *
     *{@inheritDoc}
     */
    public void init(  )
    {
    }

    /**
     *
     *{@inheritDoc}
     */
    public void doDeleteProduct( int nIdProduct )
    {
        _daoProduct.remove( nIdProduct );
        // Remove poster
        _daoProductImage.remove( nIdProduct );
    }

    /**
     *
     *{@inheritDoc}
     */
    public ShowDTO getProduct( int nIdProduct )
    {
        return ShowDTO.convertEntity( _daoProduct.findById( nIdProduct ) );
    }

    /**
     *
     *{@inheritDoc}
     */
    public List<ShowDTO> findByFilter( ProductFilter filter )
    {
        return ShowDTO.convertEntityList( _daoProduct.findByFilter( filter ) );
    }

    /**
     * {@inheritDoc}
     *
     */
    @Transactional( readOnly = false, propagation = Propagation.REQUIRES_NEW )
    public void updateProduct( ShowDTO product )
    {
        _daoProduct.update( product.convert( ) );
    }

    /**
     * Return a filtered list of product
     * @param filter the product filter
     * @param paginationProperties the pagination properties
     * @return list of product
     */
    public ResultList<ShowDTO> findByFilter( ProductFilter filter, PaginationProperties paginationProperties )
    {
        return ShowDTO.convertEntityList( _daoProduct.findByFilter( filter, paginationProperties ) );
    }

    /**
     * {@inheritDoc}
     * @throws ValidationException
     */
    @Transactional( readOnly = false, propagation = Propagation.REQUIRES_NEW )
    public ShowDTO doSaveProduct( ShowDTO product, File[] filePosterArray ) throws ValidationException
    {
    	// Start date must be before end date
    	if ( DateUtils.getDate( product.getStartDate( ), false ).after( DateUtils.getDate( product.getEndDate( ), false ) ) )
    	{
    		throw new BusinessException( product, MESSAGE_ERROR_PRODUCT_DATE_CHEVAUCHE );
    	}

        Product productEntity = product.convert( );

        List<Product> listeProduct = _daoProduct.getAllByName( product.getName( ) );
        if ( product.getId( ) != null && product.getId( ) > 0 )
        {
            //Update
            if ( listeProduct != null
                    && ( listeProduct.size( ) > 1 || listeProduct.size( ) == 1
                            && !listeProduct.get( 0 ).getId( ).equals( product.getId( ) ) ) )
            {
                throw new BusinessException( product, MESSAGE_ERROR_PRODUCT_NAME_MUST_BE_UNIQUE );
            }

            _daoProduct.update( productEntity );
        }
        else
        {
            //Create
            if ( listeProduct != null && listeProduct.size( ) > 0 )
            {
                throw new BusinessException( product, MESSAGE_ERROR_PRODUCT_NAME_MUST_BE_UNIQUE );
            }
            _daoProduct.create( productEntity );
        }
        
        // Save poster images
        if ( filePosterArray != null )
        {
            // try
            // {
            _daoProductImage.saveImage( productEntity.getId( ), filePosterArray[0], filePosterArray[1] );
            // }
            // catch ( FileNotFoundException e )
            // {
            // throw new TechnicalException(
            // "Erreur lors de l'enregistrement des images du poster : "
            // + e.getMessage( ), e );
            // }
        }

        return ShowDTO.convertEntity( productEntity );
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.paris.lutece.plugins.stock.modules.tickets.service.IShowService#findById
     * (java.lang.Integer)
     */
    /**
     * {@inheritDoc}
     */
    public ShowDTO findById( Integer nIdProduct )
    {
        return ShowDTO.convertEntity( _daoProduct.findById( nIdProduct ) );
    }

    /**
     * Return a list of products
     * @param paginationProperties the pagination properties
     * @return list of product
     */
    public ResultList<ShowDTO> getAllProduct( PaginationProperties paginationProperties )
    {
        return ShowDTO.convertEntityList( _daoProduct.findAll( paginationProperties ) );
    }

    /**
     * {@inheritDoc}
     */
    public List<ShowDTO> findAll( )
    {
        return ShowDTO.convertEntityList( _daoProduct.findAll( ) );
    }


        /**
     * {@inheritDoc}
     */
    public List<ShowDTO> getCurrentProduct( List<String> orderList, PaginationProperties paginator )
    {
        return ShowDTO.convertEntityList( _daoProduct.getCurrentProduct( orderList, paginator ) );
    }


        /**
     * {@inheritDoc}
     */
    public List<ShowDTO> getComeProduct( List<String> ordList, PaginationProperties paginator )
    {
        return ShowDTO.convertEntityList( _daoProduct.getComeProduct( ordList, paginator ) );
    }

    /**
     * {@inheritDoc}
     */
    public List<ShowDTO> getCurrentAndComeProduct( List<String> orderList )
    {
        return ShowDTO.convertEntityList( _daoProduct.getCurrentAndComeProduct( orderList ) );
    }

    /**
     * {@inheritDoc}
     */
    public byte[] getImage( Integer idProduct )
    {
        return _daoProductImage.getImage( idProduct );
    }

    /**
     * {@inheritDoc}
     */
    public byte[] getTbImage( Integer idProduct )
    {
        return _daoProductImage.getTbImage( idProduct );
    }
}
