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
package fr.paris.lutece.plugins.stock.modules.tickets.business;

import fr.paris.lutece.plugins.stock.business.attribute.category.CategoryAttribute;
import fr.paris.lutece.plugins.stock.business.category.Category;
import fr.paris.lutece.plugins.stock.commons.AbstractDTO;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.HexColorCode;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * 
 * Class ShowCategoryDTO
 * 
 */
public class ShowCategoryDTO extends AbstractDTO<Category>
{
    /** The Constant ATTR_COLOR. */
    public static final String ATTR_COLOR = "color";

    /** The id. */
    private Integer id;

    /** The description. */
    private String description;

    /** The name. */
    @NotEmpty
    private String name;

    /** The _strColor */
    @HexColorCode
    private String color;

    @Override
    /** {inheritDoc} */
    public Integer getId( )
    {
        return id;
    }

    /**
     * Set the id.
     * 
     * @param id the category id
     */
    public void setId( Integer id )
    {
        this.id = id;
    }

    /**
     * Return the category description.
     * 
     * @return the description
     */
    public String getDescription( )
    {
        return description;
    }

    /**
     * Set the category description.
     * 
     * @param description the category description
     */
    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * Return the category name.
     * 
     * @return the name
     */
    public String getName( )
    {
        return name;
    }

    /**
     * Set the category name.
     * 
     * @param name the category name
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * Return the category color.
     * 
     * @return the color
     */
    public String getColor( )
    {
        return color;
    }

    /**
     * Set the category color.
     * 
     * @param color the category color
     */
    public void setColor( String color )
    {
        this.color = color;
    }

    /**
     * Convert a list of Category into a list of ShowCategoryDTO
     * @param listSource list of categories to convert
     * @return list of converted categories
     */
    public static ResultList<ShowCategoryDTO> convertEntityList( Collection<Category> listSource )
    {
        ResultList<ShowCategoryDTO> listDest = new ResultList<ShowCategoryDTO>( );

        if ( listSource instanceof ResultList )
        {
            listDest.setTotalResult( ( (ResultList<Category>) listSource ).getTotalResult( ) );
        }

        for ( Category source : listSource )
        {
            listDest.add( convertEntity( source ) );
        }

        return listDest;
    }

    /**
     * Convert a category into a ShowCategory
     * 
     * @param source the Category object to convert
     * @return the converted category
     */
    public static ShowCategoryDTO convertEntity( Category source )
    {
        Mapper mapper = (Mapper) SpringContextService.getBean( "mapper" );
        ShowCategoryDTO showCategory = mapper.map( source, ShowCategoryDTO.class );

        Set<CategoryAttribute> attributeList = source.getAttributeList( );
        if ( attributeList != null )
        {
            for ( CategoryAttribute attribute : attributeList )
            {
                if ( ATTR_COLOR.equals( attribute.getKey( ) ) )
                {
                    showCategory.setColor( attribute.getValue( ) );
                }
            }
        }

        return showCategory;
    }

    @Override
    /** {inheritDoc} */
    public Category convert( )
    {
        Category category = mapper.map( this, Category.class );

        if ( StringUtils.isNotEmpty( this.getColor( ) ) )
        {
            category.getAttributeList( ).add( new CategoryAttribute( ATTR_COLOR, this.getColor( ), category ) );
        }

        return category;
    }

}
