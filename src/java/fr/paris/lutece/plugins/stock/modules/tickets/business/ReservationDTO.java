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

import fr.paris.lutece.plugins.stock.business.attribute.purchase.PurchaseAttribute;
import fr.paris.lutece.plugins.stock.business.attribute.purchase.PurchaseAttributeDate;
import fr.paris.lutece.plugins.stock.business.purchase.IPurchaseDTO;
import fr.paris.lutece.plugins.stock.business.purchase.Purchase;
import fr.paris.lutece.plugins.stock.commons.AbstractDTO;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.DateFormat;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.ValidId;
import fr.paris.lutece.plugins.stock.modules.tickets.utils.Constants;
import fr.paris.lutece.plugins.stock.utils.DateUtils;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import org.apache.commons.lang.StringUtils;

import org.dozer.Mapper;

import java.sql.Timestamp;

import java.util.Collection;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO for Reservation
 *
 * @author nmoitry
 */
public class ReservationDTO extends AbstractDTO<Purchase> implements IPurchaseDTO
{
    private Integer _id;
    @NotEmpty
    private String _userName;
    @ValidId
    private SeanceDTO _offer = new SeanceDTO( );
    @Min( value = 1 )
    @NotNull
    private Integer _quantity;
    @DateFormat( format = "dd/MM/yyyy" )
    @NotEmpty
    private String _date;
    private String _heure;
    @NotEmpty
    @Email
    private String _emailAgent;
    @NotEmpty
    private String _nameAgent;
    @NotEmpty
    private String _firstNameAgent;

    /**
     * If true, the line is to display (pagination)
     */
    private boolean toDisplay;

    /**
     * @return the id
     */
    public Integer getId( )
    {
        return _id;
    }

    /**
     * Sets the id.
     *
     * @param idPurchase
     *            the new id
     */
    public void setId( Integer idPurchase )
    {
        this._id = idPurchase;
    }

    /**
     * @return the userName
     */
    public String getUserName( )
    {
        return _userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName
     *            the userName to set
     */
    public void setUserName( String userName )
    {
        this._userName = userName;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity( )
    {
        return _quantity;
    }

    /**
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity( Integer quantity )
    {
        this._quantity = quantity;
    }

    /**
     * @return the date
     */
    public String getDate( )
    {
        return _date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate( String date )
    {
        this._date = date;
    }

    /**
     * @return the toDisplay
     */
    public boolean isToDisplay( )
    {
        return this.toDisplay;
    }

    /**
     * @param toDisplay
     *            the toDisplay to set
     */
    public void setToDisplay( boolean toDisplay )
    {
        this.toDisplay = toDisplay;
    }

    /**
     * Convert entity list.
     *
     * @param listSource
     *            the list source
     * @return the result list
     */
    public static ResultList<ReservationDTO> convertEntityList( Collection<Purchase> listSource )
    {
        ResultList<ReservationDTO> listDest = new ResultList<>( );

        if ( listSource instanceof ResultList )
        {
            listDest.setTotalResult( ( (ResultList<Purchase>) listSource ).getTotalResult( ) );
        }

        for ( Purchase source : listSource )
        {
            listDest.add( convertEntity( source ) );
        }

        return listDest;
    }

    /**
     * Convert entity for pagination
     * 
     * @param source
     *            the source
     * @return the reservation dto
     */
    public static ReservationDTO convertEntity( Purchase source )
    {
        Mapper mapper = SpringContextService.getBean( Constants.ATTR_MAPPER );
        ReservationDTO reservation = mapper.map( source, ReservationDTO.class );
        SeanceDTO offer = SeanceDTO.convertEntity( source.getOffer( ) );
        reservation.setOffer( offer );

        Map<String, Timestamp> attributeDateList = source.getAttributeDateMap( );

        if ( attributeDateList != null && attributeDateList.get( Constants.ATTR_DATE ) != null )
        {
            reservation.setDate( DateUtils.getDateFr( attributeDateList.get( Constants.ATTR_DATE ) ) );
        }

        Map<String, String> attributeList = source.getAttributeMap( );

        if ( attributeList != null )
        {
            if ( attributeList.get( Constants.ATTR_NAME_AGENT ) != null )
            {
                reservation.setNameAgent( attributeList.get( Constants.ATTR_NAME_AGENT ) );
            }

            if ( attributeList.get( Constants.ATTR_FIRSTNAME_AGENT ) != null )
            {
                reservation.setFirstNameAgent( attributeList.get( Constants.ATTR_FIRSTNAME_AGENT ) );
            }

            if ( attributeList.get( Constants.ATTR_EMAIL_AGENT ) != null )
            {
                reservation.setEmailAgent( attributeList.get( Constants.ATTR_EMAIL_AGENT ) );
            }
        }

        reservation.setToDisplay( true );

        return reservation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.stock.commons.AbstractDTO#convert()
     */
    public Purchase convert( )
    {
        Purchase purchase = mapper.map( this, Purchase.class );

        if ( StringUtils.isNotEmpty( this.getDate( ) ) )
        {
            purchase.getAttributeDateList( ).add( new PurchaseAttributeDate( Constants.ATTR_DATE, DateUtils.getDate( this.getDate( ), false ), purchase ) );
        }

        if ( StringUtils.isNotEmpty( this.getNameAgent( ) ) )
        {
            purchase.getAttributeList( ).add( new PurchaseAttribute( Constants.ATTR_NAME_AGENT, this.getNameAgent( ), purchase ) );
        }

        if ( StringUtils.isNotEmpty( this.getFirstNameAgent( ) ) )
        {
            purchase.getAttributeList( ).add( new PurchaseAttribute( Constants.ATTR_FIRSTNAME_AGENT, this.getFirstNameAgent( ), purchase ) );
        }

        if ( StringUtils.isNotEmpty( this.getEmailAgent( ) ) )
        {
            purchase.getAttributeList( ).add( new PurchaseAttribute( Constants.ATTR_EMAIL_AGENT, this.getEmailAgent( ), purchase ) );
        }

        return purchase;
    }

    /**
     * set the offer to offer
     * 
     * @param offer
     *            the offer
     */
    public void setOffer( SeanceDTO offer )
    {
        this._offer = offer;
    }

    /**
     * return the offer
     * 
     * @return the offer
     */
    public SeanceDTO getOffer( )
    {
        return this._offer;
    }

    /**
     * @param nameAgent
     *            the nameAgent to set
     */
    public void setNameAgent( String nameAgent )
    {
        this._nameAgent = nameAgent;
    }

    /**
     * @return the nameAgent
     */
    public String getNameAgent( )
    {
        return _nameAgent;
    }

    /**
     * @param firstNameAgent
     *            the firstNameAgent to set
     */
    public void setFirstNameAgent( String firstNameAgent )
    {
        this._firstNameAgent = firstNameAgent;
    }

    /**
     * @return the firstNameAgent
     */
    public String getFirstNameAgent( )
    {
        return _firstNameAgent;
    }

    /**
     * @return the emailAgent
     */
    public String getEmailAgent( )
    {
        return _emailAgent;
    }

    /**
     * @param emailAgent
     *            the emailAgent to set
     */
    public void setEmailAgent( String emailAgent )
    {
        this._emailAgent = emailAgent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.stock.business.purchase.IPurchaseDTO#getOfferId()
     */
    public Integer getOfferId( )
    {
        return this.getOffer( ).getId( );
    }

    /**
     * Set the heure
     * 
     * @param heure
     *            the heure to set
     */
    public void setHeure( String heure )
    {
        this._heure = heure;
    }

    @Override
    public String getHeure( )
    {
        return _heure;
    }
}
