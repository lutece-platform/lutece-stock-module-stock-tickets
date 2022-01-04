/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
import fr.paris.lutece.plugins.stock.modules.tickets.utils.Constants;
import fr.paris.lutece.plugins.stock.utils.DateUtils;
import fr.paris.lutece.portal.service.resource.IExtendableResource;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DOCUMENT ME!.
 *
 * @author abataille
 */
public class ShowDTO extends AbstractDTO<Product> implements IExtendableResource
{


    /** The id. */
    private Integer _id;

    /** The description. */
    private String _description;

    /** The name. */
    @NotEmpty
    private String _name;

    /** The price. */
    @Min( 0 )
    private Float _price;

    /** The id category. */
    @NotNull
    @Min( value = 1, message = "la catégorie est obligatoire" )
    private Integer _idCategory;

    /** The id provider. */
    @NotNull
    @Min( value = 1, message = "la salle est obligatoire" )
    private Integer _idProvider;

    /** The with. */
    private String _with;

    /** The start date. */
    @DateFormat( format = "dd/MM/yyyy" )
    @NotEmpty
    private String _startDate;

    /** The end date. */
    @DateFormat( format = "dd/MM/yyyy" )
    @NotEmpty
    @AfterCurrentDate
    private String _endDate;

    /** The update date. */
    @DateFormat( format = "dd/MM/yyyy" )
    private String _updateDate;

    /** The website. */
    @URL
    private String _website;

    /** The poster name. */
    private String _posterName;

    /** The provider name. */
    private String _providerName;

    /** The provider Address. */
    private String _providerAddress;

    /** The provider mail. */
    private String _providerMail;

    /** The category name. */
    private String _categoryName;

    /** The target public. */
    private String _targetPublic;

    /** The alaffiche. */
    private Boolean _alaffiche = Boolean.FALSE;

    /** The categoryColor */
    private String _categoryColor;

    /** The subscribable */
    private boolean _subscribable;

    private Integer [ ] _idContact;
    
    private boolean _availableInvitation = false;
    
    private boolean _availableChildInvitation = false;
    
    private boolean _availableReducedPrice = false;

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public Integer getId( )
    {
        return _id;
    }

    /**
     * Sets the id.
     *
     * @param idProduct
     *            the new id
     */
    public void setId( Integer idProduct )
    {
        _id = idProduct;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription( )
    {
        return _description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the description to set
     */
    public void setDescription( String description )
    {
        this._description = description;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName( )
    {
        return _name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name to set
     */
    public void setName( String name )
    {
        this._name = name;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public Float getPrice( )
    {
        return _price;
    }

    /**
     * Sets the price.
     *
     * @param price
     *            the price to set
     */
    public void setPrice( Float price )
    {
        this._price = price;
    }

    /**
     * Gets the id category.
     *
     * @return the idCategory
     */
    public Integer getIdCategory( )
    {
        return _idCategory;
    }

    /**
     * Sets the id category.
     *
     * @param idCategory
     *            the idCategory to set
     */
    public void setIdCategory( Integer idCategory )
    {
        this._idCategory = idCategory;
    }

    /**
     * Gets the id provider.
     *
     * @return the idProvider
     */
    public Integer getIdProvider( )
    {
        return _idProvider;
    }

    /**
     * Sets the id provider.
     *
     * @param idProvider
     *            the idProvider to set
     */
    public void setIdProvider( Integer idProvider )
    {
        this._idProvider = idProvider;
    }

    /**
     * Gets the with.
     *
     * @return the with
     */
    public String getWith( )
    {
        return _with;
    }

    /**
     * Sets the with.
     *
     * @param with
     *            the with to set
     */
    public void setWith( String with )
    {
        this._with = with;
    }

    /**
     * Gets the start date.
     *
     * @return the startDate
     */
    public String getStartDate( )
    {
        return _startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate( String startDate )
    {
        this._startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the endDate
     */
    public String getEndDate( )
    {
        return _endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate( String endDate )
    {
        this._endDate = endDate;
    }

    /**
     * Gets the update date.
     *
     * @return the updateDate
     */
    public String getUpdateDate( )
    {
        return _updateDate;
    }

    /**
     * Sets the update date.
     *
     * @param updateDate
     *            the updateDate to set
     */
    public void setUpdateDate( String updateDate )
    {
        this._updateDate = updateDate;
    }

    /**
     * Gets the website.
     *
     * @return the website
     */
    public String getWebsite( )
    {
        return _website;
    }

    /**
     * Sets the website.
     *
     * @param website
     *            the website to set
     */
    public void setWebsite( String website )
    {
        this._website = website;
    }

    /**
     * Gets the poster name.
     *
     * @return the posterName
     */
    public String getPosterName( )
    {
        return _posterName;
    }

    /**
     * Sets the poster name.
     *
     * @param posterName
     *            the posterName to set
     */
    public void setPosterName( String posterName )
    {
        this._posterName = posterName;
    }

    /**
     * Gets the provider name.
     *
     * @return the providerName
     */
    public String getProviderName( )
    {
        return _providerName;
    }

    /**
     * Sets the provider name.
     *
     * @param providerName
     *            the providerName to set
     */
    public void setProviderName( String providerName )
    {
        this._providerName = providerName;
    }

    /**
     * Gets the provider Address.
     *
     * @return the providerAddress
     */
    public String getProviderAddress( )
    {
        return _providerAddress;
    }

    /**
     * Sets the provider Address.
     *
     * @param providerAddress
     *            the providerAddress to set
     */
    public void setProviderAddress( String providerAddress )
    {
        this._providerAddress = providerAddress;
    }

    /**
     * Gets the category name.
     *
     * @return the categoryrName
     */
    public String getCategoryName( )
    {
        return _categoryName;
    }

    /**
     * Sets the category name.
     *
     * @param categoryName
     *            the new category name
     */
    public void setCategoryName( String categoryName )
    {
        this._categoryName = categoryName;
    }

    /**
     * Get the subscribable
     * 
     * @return subscribable
     */
    public boolean isSubscribable( )
    {
        return _subscribable;
    }

    /**
     * Set the subscribable
     * 
     * @param subscribable
     */
    public void setSubscribable( boolean subscribable )
    {
        this._subscribable = subscribable;
    }

    /**
     * Convert entity list.
     *
     * @param listSource
     *            the list source
     * @return the result list
     */
    public static ResultList<ShowDTO> convertEntityList( Collection<Product> listSource )
    {
        ResultList<ShowDTO> listDest = new ResultList<>( );

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
     * @param source
     *            the source
     * @return the show dto
     */
    public static ShowDTO convertEntity( Product source )
    {
        Mapper mapper = SpringContextService.getBean( Constants.ATTR_MAPPER );
        ShowDTO show = mapper.map( source, ShowDTO.class );

        Set<ProductAttributeDate> attributeDateList = source.getAttributeDateList( );

        if ( attributeDateList != null )
        {
            for ( ProductAttributeDate attribute : attributeDateList )
            {
                if ( Constants.ATTR_START.equals( attribute.getKey( ) ) )
                {
                    show.setStartDate( DateUtils.getDateFr( attribute.getValue( ) ) );
                }
                if ( Constants.ATTR_END.equals( attribute.getKey( ) ) )
                {
                    show.setEndDate( DateUtils.getDateFr( attribute.getValue( ) ) );
                }
                if ( Constants.ATTR_DATE_UPDATE.equals( attribute.getKey( ) ) )
                {
                    show.setUpdateDate( DateUtils.getDateFr( attribute.getValue( ) ) );
                }
            }
        }

        Map<String, BigDecimal> attributeNumList = source.getAttributeNumMap( );

        if ( attributeNumList != null )
        {
            if ( attributeNumList.get( Constants.ATTR_A_LAFFICHE ) != null )
            {
                show.setAlaffiche( attributeNumList.get( Constants.ATTR_A_LAFFICHE ).intValue( ) == 1 );
            }

            int nNbContacts = 0;
            List<Integer> listIdContact = new ArrayList<>( );
            BigDecimal idContact = attributeNumList.get( Constants.ATTR_ID_CONTACT );

            while ( idContact != null )
            {
                listIdContact.add( idContact.intValue( ) );
                nNbContacts++;
                idContact = attributeNumList.get( Constants.ATTR_ID_CONTACT + nNbContacts );
            }

            Integer [ ] arrayIdContact = new Integer [ nNbContacts];
            nNbContacts = 0;

            for ( Integer nIdContact : listIdContact )
            {
                arrayIdContact [nNbContacts] = nIdContact;
                nNbContacts++;
            }

            show.setIdContact( arrayIdContact );
        }

        Set<ProductAttribute> attributeList = source.getAttributeList( );

        if ( attributeList != null )
        {
            for ( ProductAttribute attribute : attributeList )
            {
                if ( Constants.ATTR_WITH.equals( attribute.getKey( ) ) )
                {
                    show.setWith( attribute.getValue( ) );
                }

                if ( Constants.ATTR_WEBSITE.equals( attribute.getKey( ) ) )
                {
                    show.setWebsite( attribute.getValue( ) );
                }

                if ( Constants.ATTR_POSTER.equals( attribute.getKey( ) ) )
                {
                    show.setPosterName( attribute.getValue( ) );
                }

                if ( Constants.ATTR_PUBLIC.equals( attribute.getKey( ) ) )
                {
                    show.setTargetPublic( attribute.getValue( ) );
                }

                // Subscription for users
                if ( Constants.ATTR_SUBSCRIBABLE.equals( attribute.getKey( ) ) )
                {
                    show.setSubscribable( attribute.getValue( ).equals( Constants.STRING_TRUE ) );
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
            product.getAttributeDateList( ).add( new ProductAttributeDate( Constants.ATTR_END, DateUtils.getDate( getEndDate( ), false ), product ) );
        }

        if ( StringUtils.isNotEmpty( getStartDate( ) ) )
        {
            product.getAttributeDateList( ).add( new ProductAttributeDate( Constants.ATTR_START, DateUtils.getDate( getStartDate( ), false ), product ) );
        }

        if ( StringUtils.isNotEmpty( getUpdateDate( ) ) )
        {
            product.getAttributeDateList( ).add( new ProductAttributeDate( Constants.ATTR_DATE_UPDATE, DateUtils.getDate( getUpdateDate( ), false ), product ) );
        }

        if ( StringUtils.isNotEmpty( getWith( ) ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( Constants.ATTR_WITH, getWith( ), product ) );
        }

        if ( StringUtils.isNotEmpty( getWebsite( ) ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( Constants.ATTR_WEBSITE, getWebsite( ), product ) );
        }

        if ( StringUtils.isNotEmpty( getPosterName( ) ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( Constants.ATTR_POSTER, getPosterName( ), product ) );
        }

        if ( StringUtils.isNotEmpty( getTargetPublic( ) ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( Constants.ATTR_PUBLIC, getTargetPublic( ), product ) );
        }

        if ( getAlaffiche( ) != null )
        {
            product.getAttributeNumList( ).add( new ProductAttributeNum( Constants.ATTR_A_LAFFICHE, ( getAlaffiche( ) ? BigDecimal.ONE : BigDecimal.ZERO ), product ) );
        }

        // Subscription to the show (send an email)
        if ( this.isSubscribable( ) )
        {
            product.getAttributeList( ).add( new ProductAttribute( Constants.ATTR_SUBSCRIBABLE, Constants.STRING_TRUE, product ) );
        }
        else
        {
            product.getAttributeList( ).add( new ProductAttribute( Constants.ATTR_SUBSCRIBABLE, Constants.STRING_FALSE, product ) );
        }

        if ( ( this._idContact != null ) && ( this._idContact.length > 0 ) )
        {
            int nNbContact = 0;

            for ( Integer nIdContact : _idContact )
            {
                product.getAttributeNumList( ).add(
                        new ProductAttributeNum( ( nNbContact == 0 ) ? Constants.ATTR_ID_CONTACT : ( Constants.ATTR_ID_CONTACT + nNbContact ), BigDecimal.valueOf( nIdContact ),
                                product ) );
                nNbContact++;
            }
        }

        return product;
    }

    /**
     * Sets the provider mail.
     *
     * @param providerMail
     *            the providerMail to set
     */
    public void setProviderMail( String providerMail )
    {
        this._providerMail = providerMail;
    }

    /**
     * Gets the provider mail.
     *
     * @return the providerMail
     */
    public String getProviderMail( )
    {
        return _providerMail;
    }

    /**
     * Gets the target public.
     *
     * @return the targetPublic
     */
    public String getTargetPublic( )
    {
        return _targetPublic;
    }

    /**
     * Sets the target public.
     *
     * @param targetPublic
     *            the targetPublic to set
     */
    public void setTargetPublic( String targetPublic )
    {
        this._targetPublic = targetPublic;
    }

    /**
     * Gets the alaffiche.
     *
     * @return the aLaffiche
     */
    public Boolean getAlaffiche( )
    {
        return _alaffiche;
    }

    /**
     * Sets the alaffiche.
     *
     * @param aLaffiche
     *            the aLaffiche to set
     */
    public void setAlaffiche( Boolean aLaffiche )
    {
        _alaffiche = aLaffiche;
    }

    /**
     * Get the category color.
     *
     * @return the categoryColor, or an empty string if empty
     */
    public String getCategoryColor( )
    {
        return _categoryColor;
    }

    /**
     * Set the category color.
     *
     * @param categoryColor
     *            the categoryColor to set
     */
    public void setCategoryColor( String categoryColor )
    {
        this._categoryColor = categoryColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdExtendableResource( )
    {
        return Integer.toString( _id );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExtendableResourceType( )
    {
        return Constants.PROPERTY_RESOURCE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExtendableResourceName( )
    {
        return _name;
    }

    /**
     * {@inheritDoc}
     */

    // @Override
    public String getExtendableResourceDescription( )
    {
        return _description;
    }

    /**
     * {@inheritDoc}
     */

    // @Override
    public String getExtendableResourceImageUrl( )
    {
        // No image
        return null;
    }

    /**
     * 
     * @return the contact
     */
    public Integer [ ] getIdContact( )
    {
        return _idContact;
    }

    /**
     * Set the contact
     * 
     * @param idContact
     *            the new contact to set
     */
    public void setIdContact( Integer [ ] idContact )
    {
        this._idContact = idContact;
    }
    
    /**
	 * @return the availableInvitation
	 */
	public boolean isAvailableInvitation( )
	{
		return _availableInvitation;
	}

	/**
	 * @param availableInvitation the availableInvitation to set
	 */
	public void setAvailableInvitation( boolean availableInvitation )
	{
		this._availableInvitation = availableInvitation;
	}

	/**
	 * @return the availableChildInvitation
	 */
	public boolean isAvailableChildInvitation( )
	{
		return _availableChildInvitation;
	}

	/**
	 * @param availableChildInvitation the availableChildInvitation to set
	 */
	public void setAvailableChildInvitation( boolean availableChildInvitation )
	{
		this._availableChildInvitation = availableChildInvitation;
	}

	/**
	 * @return the availableReducedPrice
	 */
	public boolean isAvailableReducedPrice( )
	{
		return _availableReducedPrice;
	}

	/**
	 * @param availableReducedPrice the availableReducedPrice to set
	 */
	public void setAvailableReducedPrice( boolean availableReducedPrice )
	{
		this._availableReducedPrice = availableReducedPrice;
	}
}
