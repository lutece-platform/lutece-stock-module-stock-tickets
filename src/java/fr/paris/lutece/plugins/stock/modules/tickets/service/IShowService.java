/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import fr.paris.lutece.plugins.stock.business.product.ProductFilter;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.commons.exception.ValidationException;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ShowDTO;

import java.io.File;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


/**
 * IProductService.
 */
@Transactional
public interface IShowService
{


    /**
     * Init the product service and register it in the global product service.
     */
    void init(  );

    /**
     * Delete the product.
     * 
     * @param nIdProduct the id of the product to delete
     */
    void doDeleteProduct( int nIdProduct );


    /**
     * Return the product object.
     * 
     * @param nIdProduct the product id
     * @return the product
     */
    ShowDTO getProduct( int nIdProduct );

    /**
     * Return a filtered list of product.
     * 
     * @param filter the product filter
     * @return list of product
     */
    List<ShowDTO> findByFilter( ProductFilter filter );


    /**
     * Return a filtered list of product.
     * 
     * @param filter the product filter
     * @param paginationProperties the pagination properties
     * @return list of product
     */
    ResultList<ShowDTO> findByFilter( ProductFilter filter, PaginationProperties paginationProperties );

    /**
     * Modifies a product.
     * 
     * @param product The product to modify in database
     * @param filePosterArray files poster
     * @return the product
     * @throws ValidationException the validation exception
     */
    ShowDTO doSaveProduct( ShowDTO product, File[] filePosterArray ) throws ValidationException;

    /**
     * Find by id.
     * 
     * @param nIdProduct the n id product
     * @return the show dto
     */
    ShowDTO findById( Integer nIdProduct );

    /**
     * Return a list of all products.
     * 
     * @param paginationProperties the pagination properties
     * @return list of product
     */
    ResultList<ShowDTO> getAllProduct( PaginationProperties paginationProperties );

    /**
     * Return a list of all products.
     * 
     * @return list of product
     */
	List<ShowDTO> findAll( );

    /**
     * Return a list of products with date_end after the current date.
     * 
     * @param orderList the list of orders
     * @param paginator the paginator
     * @return list of product
     */
    List<ShowDTO> getCurrentProduct( List<String> orderList, PaginationProperties paginator );

    /**
     * Return a list of product with date_start after the current date.
     * 
     * @param orderList the order list
     * @param paginator the paginator
     * @return list of product
     */
    List<ShowDTO> getComeProduct( List<String> orderList, PaginationProperties paginator );

    /**
     * Return a list of product with date_end after the current date.
     * 
     * @param orderList the order list
     * @return list of product
     */
    List<ShowDTO> getCurrentAndComeProduct( List<String> orderList );

    /**
     * Gets the image data.
     * 
     * @param idProduct the id product
     * @return the image
     */
    byte[] getImage( Integer idProduct );

    /**
     * Gets the thumbnail image.
     * 
     * @param idProduct the id product
     * @return the tb image
     */
    byte[] getTbImage( Integer idProduct );
}
