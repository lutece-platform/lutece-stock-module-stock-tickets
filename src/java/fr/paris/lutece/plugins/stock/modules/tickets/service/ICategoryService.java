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

import fr.paris.lutece.plugins.stock.business.category.Category;
import fr.paris.lutece.plugins.stock.business.category.CategoryFilter;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.commons.exception.ValidationException;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;


/**
 * ICategoryService.
 */
@Transactional
public interface ICategoryService
{

    /**
     * Init the object.
     */
    void init(  );

    /**
     * Creates a category.
     * 
     * @param category The category to create in database
     * @return error if occurs
     */
    String doCreateCategory( Category category );

    /**
     * Modifies a category.
     * 
     * @param category The category to modify in database
     * @return error if occurs
     */
    String doModifyCategory( Category category );

    /**
     * Deletes a category.
     * 
     * @param nIdCategory The category id to delete in database
     */
    void doDeleteCategory( int nIdCategory );

    /**
     * Get the category with the given id.
     * 
     * @param nIdCategory The category id
     * @return The category with the given id
     */
    Category getCategory( int nIdCategory );

    /**
     * Finds a category and fetch parent.
     * 
     * @param nIdCategory the id of the searched category
     * @return the category
     */
    Category getCategoryWithParent( int nIdCategory );

    /**
     * Finds a category and all his subcategories and fetch children.
     * 
     * @param nIdCategory the id of upper category
     * @return the category
     */
    Category getCategoryWithChildren( Integer nIdCategory );

    /**
     * Finds all categories which match the given filter.
     * 
     * @param filter The filter
     * @param paginationProperties the pagination properties
     * @return A list of categories which match the given filter
     */
    ResultList<Category> findByFilter( CategoryFilter filter, PaginationProperties paginationProperties );

	    /**
     * Finds all categories (first level) and fetch children.
     * 
     * @return all first level categories
     */
	List<Category> findAllFirstLevelWithChildren();

    /**
     * Finds all category's children and fetch children.
     * 
     * @param idCategory the id of upper category
     * @return all category's children
     */
    List<Category> findAllChildrenWithChildren( Integer idCategory );

    /**
     * Finds a category and all his subcategories and fetch children.
     * 
     * @param nIdCategory the id of upper category
     * @return the category
     */
    Category findByPrimarykeyWithChildren( Integer nIdCategory );

    /**
     * Finds a category and fetch parent.
     * 
     * @param nIdCategory the id of the searched category
     * @return the category
     */
    Category findByIdWithParent( Integer nIdCategory );

    /**
     * Finds a category and fetch product.
     * 
     * @param nIdCategory the id of the searched category
     * @return the category
     */
    Category findByIdWithProduct( Integer nIdCategory );

    /**
     * Finds by filter and fetch children.
     * 
     * @param filter the filter
     * @return the category list
     */
    List<Category> findByFilterWithChildren( CategoryFilter filter );

    /**
     * Returns all.
     * 
     * @return the list
     */
    List<Category> findAll( );

    /**
     * Find by id.
     * 
     * @param nIdCategory the n id category
     * @return the category
     */
    Category findById( Integer nIdCategory );

    /**
     * Update the category.
     * 
     * @param category the category
     */
    void update( Category category );

    /**
     * Modifies a category.
     * 
     * @param category The category to modify in database
     * @throws ValidationException the validation exception
     */
    void doSaveCategory( Category category ) throws ValidationException;

    /**
     * Return all category.
     * 
     * @param orderList list of order
     * @return the list of provider
     */
	List<Category> findAll( List<String> orderList );
}
