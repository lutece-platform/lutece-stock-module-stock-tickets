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
package fr.paris.lutece.plugins.stock.modules.tickets.business;

import fr.paris.lutece.plugins.stock.business.attribute.product.ProductAttribute;
import fr.paris.lutece.plugins.stock.business.attribute.product.ProductAttributeDate;
import fr.paris.lutece.plugins.stock.business.attribute.product.ProductAttributeNum;
import fr.paris.lutece.plugins.stock.business.product.Product;
import fr.paris.lutece.plugins.stock.commons.AbstractDTO;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.AfterCurrentDate;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.DateFormat;
import fr.paris.lutece.plugins.stock.utils.DateUtils;
import fr.paris.lutece.portal.service.resource.IExtendableResource;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;


/**
 * DOCUMENT ME!.
 *
 * @author abataille
 */
public class ShowDTO extends AbstractDTO<Product> implements IExtendableResource
{

    /** The Constant STRING_TRUE. */
    public static final String STRING_TRUE = "true";

    /** The Constant STRING_FALSE. */
    public static final String STRING_FALSE = "false";

	/** The Constant PROPERTY_RESOURCE_TYPE. */
	public static final String PROPERTY_RESOURCE_TYPE = "stock-product";

    /** The Constant ATTR_DATE_START. */
    public static final String ATTR_DATE_START = "start";

    /** The Constant ATTR_DATE_END. */
    public static final String ATTR_DATE_END = "end";

    /** The Constant ATTR_WITH. */
    public static final String ATTR_WITH = "with";

    /** The Constant ATTR_WEBSITE. */
    public static final String ATTR_WEBSITE = "website";

    /** The Constant ATTR_POSTER. */
    public static final String ATTR_POSTER = "posterName";

    /** The Constant ATTR_PUBLIC. */
    public static final String ATTR_PUBLIC = "public";

    /** The Constant ATTR_A_LAFFICHE. */
    public static final String ATTR_A_LAFFICHE = "aLaffiche";

    /** The Constant ATTR_SUBSCRIBABLE. */
    public static final String ATTR_SUBSCRIBABLE = "subscribable";

    /** The id. */
    private Integer id;

    /** The description. */
    private String description;

    /** The name. */
    @NotEmpty
    private String name;

    /** The price. */
    @Min( 0 )
    private Float price;

    /** The id category. */
    @NotNull
    @Min( value = 1, message = "la catégorie est obligatoire" )
    private Integer idCategory;

    /** The id provider. */
    @NotNull
    @Min( value = 1, message = "la salle est obligatoire" )
    private Integer idProvider;

    /** The with. */
    private String with;

    /** The start date. */
    @DateFormat( format = "dd/MM/yyyy" )
    @NotEmpty
    private String startDate;

    /** The end date. */
    @DateFormat( format = "dd/MM/yyyy" )
    @NotEmpty
    @AfterCurrentDate
    private String endDate;

    /** The website. */
    @URL
    private String website;

    /** The poster name. */
    private String posterName;

    /** The provider name. */
    private String providerName;

    /** The provider Address. */
    private String providerAddress;

    /** The provider mail. */
    private String providerMail;

    /** The category name. */
    private String categoryName;

    /** The target public. */
    private String targetPublic;

    /** The alaffiche. */
    private Boolean alaffiche = Boolean.FALSE;

    /** The categoryColor */
    private String categoryColor;
    
    /** The subscribable */
    private boolean subscribable;

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
	public Integer getId( )
    {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param idProduct the new id
     */
    public void setId( Integer idProduct )
    {
        id = idProduct;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription( )
    {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description to set
     */
    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName( )
    {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public Float getPrice( )
    {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the price to set
     */
    public void setPrice( Float price )
    {
        this.price = price;
    }

    /**
     * Gets the id category.
     *
     * @return the idCategory
     */
    public Integer getIdCategory( )
    {
        return idCategory;
    }

    /**
     * Sets the id category.
     *
     * @param idCategory the idCategory to set
     */
    public void setIdCategory( Integer idCategory )
    {
        this.idCategory = idCategory;
    }

    /**
     * Gets the id provider.
     *
     * @return the idProvider
     */
    public Integer getIdProvider( )
    {
        return idProvider;
    }

    /**
     * Sets the id provider.
     *
     * @param idProvider the idProvider to set
     */
    public void setIdProvider( Integer idProvider )
    {
        this.idProvider = idProvider;
    }

    /**
     * Gets the with.
     *
     * @return the with
     */
    public String getWith( )
    {
        return with;
    }

    /**
     * Sets the with.
     *
     * @param with the with to set
     */
    public void setWith( String with )
    {
        this.with = with;
    }

    /**
     * Gets the start date.
     *
     * @return the startDate
     */
    public String getStartDate( )
    {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the startDate to set
     */
    public void setStartDate( String startDate )
    {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the endDate
     */
    public String getEndDate( )
    {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the endDate to set
     */
    public void setEndDate( String endDate )
    {
        this.endDate = endDate;
    }

    /**
     * Gets the website.
     *
     * @return the website
     */
    public String getWebsite( )
    {
        return website;
    }

    /**
     * Sets the website.
     *
     * @param website the website to set
     */
    public void setWebsite( String website )
    {
        this.website = website;
    }

    /**
     * Gets the poster name.
     *
     * @return the posterName
     */
    public String getPosterName( )
    {
        return posterName;
    }

    /**
     * Sets the poster name.
     *
     * @param posterName the posterName to set
     */
    public void setPosterName( String posterName )
    {
        this.posterName = posterName;
    }

    /**
     * Gets the provider name.
     *
     * @return the providerName
     */
    public String getProviderName( )
    {
        return providerName;
    }

    /**
     * Sets the provider name.
     *
     * @param providerName the providerName to set
     */
    public void setProviderName( String providerName )
    {
        this.providerName = providerName;
    }

    /**
     * Gets the provider Address.
     *
     * @return the providerAddress
     */
    public String getProviderAddress( )
    {
        return providerAddress;
    }

    /**
     * Sets the provider Address.
     *
     * @param providerAddress the providerAddress to set
     */
    public void setProviderAddress( String providerAddress )
    {
        this.providerAddress = providerAddress;
    }

    /**
     * Gets the category name.
     *
     * @return the categoryrName
     */
    public String getCategoryName( )
    {
        return categoryName;
    }

    /**
     * Sets the category name.
     *
     * @param categoryName the new category name
     */
    public void setCategoryName( String categoryName )
    {
        this.categoryName = categoryName;
    }

    /**
     * Get the subscribable
     * @return subscribable
     */
    public boolean isSubscribable( )
    {
        return subscribable;
    }

    /**
     * Set the subscribable
     * @param subscribable
     */
    public void setSubscribable( boolean subscribable )
    {
        this.subscribable = subscribable;
    }

    /**
     * Convert entity list.
     *
     * @param listSource the list source
     * @return the result list
     */
    public static ResultList<ShowDTO> convertEntityList( Collection<Product> listSource )
    {
        ResultList<ShowDTO> listDest = new ResultList<ShowDTO>( );
        if ( listSource instanceof ResultList )
        {
            listDest.setTotalResult( ( (ResultList<Product>) listSource ).getTotalResult( ) );
        }

        for ( Product source : listSource )
        {
            listDest.add( convertEntity( source ) );
        }

        return listDest;

    }

    /**
     * Convert entity.
     *
     * @param source the source
     * @return the show dto
     */
    public static ShowDTO convertEntity( Product source )
    {
        Mapper mapper = (Mapper) SpringContextService.getBean( "mapper" );
        ShowDTO show = mapper.map( source, ShowDTO.class );

        Set<ProductAttributeDate> attributeDateList = source.getAttributeDateList( );
        if ( attributeDateList != null )
        {

            for ( ProductAttributeDate attribute : attributeDateList )
            {
                if ( ATTR_DATE_START.equals( attribute.getKey( ) ) )
                {
                    show.setStartDate( DateUtils.getDateFr( attribute.getValue( ) ) );
                }
                else if ( ATTR_DATE_END.equals( attribute.getKey( ) ) )
                {
                    show.setEndDate( DateUtils.getDateFr( attribute.getValue( ) ) );
                }
            }
        }

        Map<String, BigDecimal> attributeNumList = source.getAttributeNumMap( );
        if ( attributeNumList != null )
        {

            if ( attributeNumList.get( ATTR_A_LAFFICHE ) != null )
            {
                show.setAlaffiche( attributeNumList.get( ATTR_A_LAFFICHE ).intValue( ) == 1 );
            }
        }
        Set<ProductAttribute> attributeList = source.getAttributeList( );
        if ( attributeList != null )
        {
            for ( ProductAttribute attribute : attributeList )
            {
                if ( ATTR_WITH.equals( attribute.getKey( ) ) )
                {
                    show.setWith( attribute.getValue( ) );
                }
                if ( ATTR_WEBSITE.equals( attribute.getKey( ) ) )
                {
                    show.setWebsite( attribute.getValue( ) );
                }
                if ( ATTR_POSTER.equals( attribute.getKey( ) ) )
                {
                    show.setPosterName( attribute.getValue( ) );
                }
                if ( ATTR_PUBLIC.equals( attribute.getKey( ) ) )
                {
                    show.setTargetPublic( attribute.getValue( ) );
                }

                //Subscription for users
                if ( ATTR_SUBSCRIBABLE.equals( attribute.getKey( ) ) )
                {
                    if ( attribute.getValue( ).equals( STRING_TRUE ) )
                    {
                        show.setSubscribable( true );
                    }
                    else
                    {
                        show.setSubscribable( false );
                    }
                }
            }
        }

        String categoryColor = null;
        if ( source.getCategory( ) != null )
        {
            categoryColor = ShowCategoryDTO.convertEntity( source.getCategory( ) ).getColor( );
        }
        show.setCategoryColor( StringUtils.defaultString( categoryColor ) );

        return show;
    }

    /** {@inheritDoc} */
    @Override
	public Product convert( )
    {
        Product product = mapper.map( this, Product.class );

        if ( StringUtils.isNotEmpty( getEndDate( ) ) )
        {
            product.getAttributeDateList( ).add(
                    new ProductAttributeDate( ATTR_DATE_END, DateUtils.getDate( getEndDate( ), false ), product ) );
        }

        if ( StringUtils.isNotEmpty( getStartDate( ) ) )
        {
            product.getAttributeDateList( ).add(
                    new ProductAttributeDate( ATTR_DATE_START, DateUtils.getDate( getStartDate( ), false ),
                            product ) );
        }

        if ( StringUtils.isNotEmpty( getWith( ) ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( ATTR_WITH, getWith( ), product ) );
        }
        if ( StringUtils.isNotEmpty( getWebsite( ) ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( ATTR_WEBSITE, getWebsite( ), product ) );
        }
        if ( StringUtils.isNotEmpty( getPosterName( ) ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( ATTR_POSTER, getPosterName( ), product ) );
        }
        if ( StringUtils.isNotEmpty( getTargetPublic( ) ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( ATTR_PUBLIC, getTargetPublic( ), product ) );
        }
        if ( getAlaffiche( ) != null )
        {
            product.getAttributeNumList( ).add(
                    new ProductAttributeNum( ATTR_A_LAFFICHE,
                            ( getAlaffiche( ) ? BigDecimal.ONE : BigDecimal.ZERO ), product ) );
        }

        //Subscription to the show (send an email)
        if ( this.isSubscribable( ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( ATTR_SUBSCRIBABLE, STRING_TRUE, product ) );
        }
        else
        {
            product.getAttributeList( ).add( new ProductAttribute( ATTR_SUBSCRIBABLE, STRING_FALSE, product ) );
        }

        return product;
    }

	    /**
     * Sets the provider mail.
     *
     * @param providerMail the providerMail to set
     */
	public void setProviderMail( String providerMail )
	{
		this.providerMail = providerMail;
	}

	    /**
     * Gets the provider mail.
     *
     * @return the providerMail
     */
	public String getProviderMail( )
	{
		return providerMail;
	}

    /**
     * Gets the target public.
     *
     * @return the targetPublic
     */
    public String getTargetPublic( )
    {
        return targetPublic;
    }

    /**
     * Sets the target public.
     *
     * @param targetPublic the targetPublic to set
     */
    public void setTargetPublic( String targetPublic )
    {
        this.targetPublic = targetPublic;
    }


    /**
     * Gets the alaffiche.
     *
     * @return the aLaffiche
     */
    public Boolean getAlaffiche( )
    {
        return alaffiche;
    }

    /**
     * Sets the alaffiche.
     *
     * @param aLaffiche the aLaffiche to set
     */
    public void setAlaffiche( Boolean aLaffiche )
    {
        alaffiche = aLaffiche;
    }

    /**
     * Get the category color.
     *
     * @return the categoryColor, or an empty string if empty
     */
    public String getCategoryColor( )
    {
        return categoryColor;
    }

    /**
     * Set the category color.
     *
     * @param categoryColor the categoryColor to set
     */
    public void setCategoryColor( String categoryColor )
    {
        this.categoryColor = categoryColor;
    }

    /**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdExtendableResource( )
	{
		return Integer.toString( id );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getExtendableResourceType( )
	{
		return PROPERTY_RESOURCE_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getExtendableResourceName( )
	{
		return name;
	}

    /**
     * {@inheritDoc}
     */
//    @Override
    public String getExtendableResourceDescription( )
    {
        return description;
    }

    /**
     * {@inheritDoc}
     */
//    @Override
    public String getExtendableResourceImageUrl( )
    {
        // No image
        return null;
    }
}
