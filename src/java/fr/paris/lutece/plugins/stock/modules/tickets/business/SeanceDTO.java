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

import fr.paris.lutece.plugins.stock.business.attribute.offer.OfferAttributeDate;
import fr.paris.lutece.plugins.stock.business.attribute.offer.OfferAttributeNum;
import fr.paris.lutece.plugins.stock.business.offer.Offer;
import fr.paris.lutece.plugins.stock.commons.AbstractDTO;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.AfterCurrentDate;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.DateFormat;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.ValidId;
import fr.paris.lutece.plugins.stock.modules.tickets.utils.Constants;
import fr.paris.lutece.plugins.stock.utils.DateUtils;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO for Seance
 */
public class SeanceDTO extends AbstractDTO<Offer>
{
    private Integer _id;
    private String _description;
    private String _name;
    @Min( value = 1 )
    private Float _reductPrice;
    @ValidId
    private ShowDTO _product = new ShowDTO( );
    @Min( value = 1, message = "le type est obligatoire" )
    private Integer _idGenre;
    private String _typeName;
    @Min( value = 0 )
    @NotNull
    private Integer _quantity;
    private Integer _initialQuantity;
    private Integer _totalQuantity;
    @DateFormat( format = "dd/MM/yyyy" )
    @NotEmpty
    @AfterCurrentDate
    private String _date;
    @DateFormat( format = "HH:mm" )
    @NotEmpty
    private String _hour;
    private String _statut;
    private Date _dateHour;
    private Integer [ ] _idContact;
    @Range( min = 1, max = 99, message = "#i18n{module.stock.tickets.validation.seanceDTO.minTickets.range}" )
    @NotNull
    private Integer _minTickets;
    @Range( min = 1, max = 99, message = "#i18n{module.stock.tickets.validation.seanceDTO.maxTickets.range}" )
    @NotNull
    private Integer _maxTickets;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getId( )
    {
        return _id;
    }

    /**
     * Sets the id.
     *
     * @param idOffer
     *            the new id
     */
    public void setId( Integer idOffer )
    {
        this._id = idOffer;
    }

    /**
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
     * @return the reductPrice
     */
    public Float getReductPrice( )
    {
        return _reductPrice;
    }

    /**
     * Sets the reduct price.
     *
     * @param reductPrice
     *            the reductPrice to set
     */
    public void setReductPrice( Float reductPrice )
    {
        this._reductPrice = reductPrice;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity( )
    {
        return _quantity;
    }

    /**
     * Sets the quantity.
     *
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity( Integer quantity )
    {
        this._quantity = quantity;
    }

    /**
     * @return the initial quantity
     */
    public Integer getInitialQuantity( )
    {
        return _initialQuantity;
    }

    /**
     * Sets the initial quantity.
     *
     * @param initialQuantity
     *            the quantity to set
     */
    public void setInitialQuantity( Integer initialQuantity )
    {
        this._initialQuantity = initialQuantity;
    }

    /**
     * @return the hour
     */
    public String getHour( )
    {
        return _hour;
    }

    public Integer getTotalQuantity( )
    {
        return _totalQuantity;
    }

    public void setTotalQuantity( Integer totalQuantity )
    {
        this._totalQuantity = totalQuantity;
    }


    /**
     * Sets the hour.
     *
     * @param hour
     *            the hour to set
     */
    public void setHour( String hour )
    {
        this._hour = hour;
    }

    /**
     * @return the date
     */
    public String getDate( )
    {
        return _date;
    }

    /**
     * Sets the date.
     *
     * @param date
     *            the date to set
     */
    public void setDate( String date )
    {
        this._date = date;
    }

    /**
     * Convert entity list from database
     *
     * @param listSource
     *            the list source
     * @return the result list
     */
    public static ResultList<SeanceDTO> convertEntityList( Collection<Offer> listSource )
    {
        ResultList<SeanceDTO> listDest = new ResultList<>( );

        if ( listSource instanceof ResultList )
        {
            listDest.setTotalResult( ( (ResultList<Offer>) listSource ).getTotalResult( ) );
        }

        SeanceDTO seanceDTO;

        for ( Offer source : listSource )
        {
            seanceDTO = convertEntity( source );
            // Add date + hour merged
            seanceDTO.setDateHour( DateUtils.mergeDateHour( source.getAttributeDateMap( ).get( Constants.ATTR_DATE ),
                    source.getAttributeDateMap( ).get( Constants.ATTR_HOUR ) ) );
            listDest.add( seanceDTO );
        }

        return listDest;
    }

    /**
     * Convert entity from database
     *
     * @param source
     *            the source
     * @return the seance dto
     */
    public static SeanceDTO convertEntity( Offer source )
    {
        Mapper mapper = SpringContextService.getBean( Constants.ATTR_MAPPER );
        SeanceDTO seance = mapper.map( source, SeanceDTO.class );

        Map<String, Timestamp> attributeDateList = source.getAttributeDateMap( );

        if ( attributeDateList != null )
        {
            Timestamp date = attributeDateList.get( Constants.ATTR_DATE );

            if ( date != null )
            {
                seance.setDate( DateUtils.getDateFr( date ) );
            }

            Timestamp hour = attributeDateList.get( Constants.ATTR_HOUR );

            if ( hour != null )
            {
                seance.setHour( DateUtils.getHourFr( hour ) );
            }

            seance.setDateHour( DateUtils.mergeDateHour( date, hour ) );
        }

        Map<String, BigDecimal> attributeNumList = source.getAttributeNumMap( );

        if ( attributeNumList != null )
        {
            if ( attributeNumList.get( Constants.ATTR_REDUCT_PRICE ) != null )
            {
                seance.setReductPrice( attributeNumList.get( Constants.ATTR_REDUCT_PRICE ).floatValue( ) );
            }

            if ( attributeNumList.get( Constants.ATTR_INIT_QUANTITY ) != null )
            {
                seance.setInitialQuantity( attributeNumList.get( Constants.ATTR_INIT_QUANTITY ).intValue( ) );
            }
            if ( attributeNumList.get( Constants.ATTR_TOTAL_QUANTITY ) != null )
            {
                seance.setTotalQuantity( attributeNumList.get( Constants.ATTR_TOTAL_QUANTITY ).intValue( ) );
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

            seance.setIdContact( arrayIdContact );

            if ( attributeNumList.get( Constants.ATTR_MIN_TICKETS ) != null )
            {
                seance.setMinTickets( attributeNumList.get( Constants.ATTR_MIN_TICKETS ).intValue( ) );
            }

            if ( attributeNumList.get( Constants.ATTR_MAX_TICKETS ) != null )
            {
                seance.setMaxTickets( attributeNumList.get( Constants.ATTR_MAX_TICKETS ).intValue( ) );
            }
        }

        return seance;
    }

    /**
     * Convert an offer to database
     * 
     * @see fr.paris.lutece.plugins.stock.commons.AbstractDTO#convert()
     */
    @Override
    public Offer convert( )
    {
        Offer offer = mapper.map( this, Offer.class );

        if ( StringUtils.isNotEmpty( this.getDate( ) ) )
        {
            offer.getAttributeDateList( ).add( new OfferAttributeDate( Constants.ATTR_DATE, DateUtils.getDate( this.getDate( ), false ), offer ) );
        }

        if ( StringUtils.isNotEmpty( this.getHour( ) ) )
        {
            offer.getAttributeDateList( ).add( new OfferAttributeDate( Constants.ATTR_HOUR, DateUtils.getHour( this.getHour( ) ), offer ) );
        }

        if ( this.getReductPrice( ) != null )
        {
            offer.getAttributeNumList( ).add( new OfferAttributeNum( Constants.ATTR_REDUCT_PRICE, BigDecimal.valueOf( this.getReductPrice( ) ), offer ) );
        }

        if ( this._initialQuantity != null )
        {
            offer.getAttributeNumList( ).add( new OfferAttributeNum( Constants.ATTR_INIT_QUANTITY, BigDecimal.valueOf( this.getInitialQuantity( ) ), offer ) );
        }
        if ( this._totalQuantity != null )
        {
            offer.getAttributeNumList( ).add( new OfferAttributeNum( Constants.ATTR_TOTAL_QUANTITY, BigDecimal.valueOf( this.getTotalQuantity( ) ), offer ) );
        }

        if ( ( this._idContact != null ) && ( this._idContact.length > 0 ) )
        {
            int nNbContact = 0;

            for ( Integer nIdContact : _idContact )
            {
                offer.getAttributeNumList( ).add(
                        new OfferAttributeNum( ( nNbContact == 0 ) ? Constants.ATTR_ID_CONTACT : ( Constants.ATTR_ID_CONTACT + nNbContact ), BigDecimal.valueOf( nIdContact ),
                                offer ) );
                nNbContact++;
            }
        }

        if ( this._minTickets != null )
        {
            offer.getAttributeNumList( ).add( new OfferAttributeNum( Constants.ATTR_MIN_TICKETS, BigDecimal.valueOf( this.getMinTickets( ) ), offer ) );
        }

        if ( this._maxTickets != null )
        {
            offer.getAttributeNumList( ).add( new OfferAttributeNum( Constants.ATTR_MAX_TICKETS, BigDecimal.valueOf( this.getMaxTickets( ) ), offer ) );
        }
        return offer;
    }

    /**
     * set the product to product
     * 
     * @param theProduct
     *            the product
     */
    public void setProduct( ShowDTO theProduct )
    {
        this._product = theProduct;
    }

    /**
     * return the product
     * 
     * @return the product
     */
    public ShowDTO getProduct( )
    {
        return _product;
    }

    /**
     * Sets the id genre.
     *
     * @param idGenre
     *            the new id genre
     */
    public void setIdGenre( Integer idGenre )
    {
        this._idGenre = idGenre;
    }

    /**
     * Gets the id genre.
     *
     * @return the id genre
     */
    public Integer getIdGenre( )
    {
        return _idGenre;
    }

    /**
     * Sets the type name.
     *
     * @param typeName
     *            the new type name
     */
    public void setTypeName( String typeName )
    {
        this._typeName = typeName;
    }

    /**
     * Gets the type name.
     *
     * @return the type name
     */
    public String getTypeName( )
    {
        return _typeName;
    }

    /**
     * Sets the statut.
     *
     * @param statut
     *            the statut to set
     */
    public void setStatut( String statut )
    {
        this._statut = statut;
    }

    /**
     * @return the statut
     */
    public String getStatut( )
    {
        return _statut;
    }

    /**
     * @return the dateHour
     */
    public Date getDateHour( )
    {
        return _dateHour;
    }

    /**
     * Sets the date hour.
     *
     * @param theDateHour
     *            the dateHour to set
     */
    public void setDateHour( Date theDateHour )
    {
        this._dateHour = theDateHour;
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
     *
     * @return the contact
     */
    public Integer [ ] getIdContact( )
    {
        return _idContact;
    }

    /**
     * @return the minTickets
     */
    public Integer getMinTickets( )
    {
        return _minTickets;
    }

    /**
     * Sets the minTickets.
     *
     * @param minTickets
     *            the minTickets to set
     */
    public void setMinTickets( Integer minTickets )
    {
        this._minTickets = minTickets;
    }

    /**
     * @return the maxTickets
     */
    public Integer getMaxTickets( )
    {
        return _maxTickets;
    }

    /**
     * Sets the maxTickets.
     *
     * @param maxTickets
     *            the maxTickets to set
     */
    public void setMaxTickets( Integer maxTickets )
    {
        this._maxTickets = maxTickets;
    }
}
