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

import fr.paris.lutece.plugins.stock.business.attribute.offer.OfferAttributeDate;
import fr.paris.lutece.plugins.stock.business.attribute.offer.OfferAttributeNum;
import fr.paris.lutece.plugins.stock.business.offer.Offer;
import fr.paris.lutece.plugins.stock.commons.AbstractDTO;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.AfterCurrentDate;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.DateFormat;
import fr.paris.lutece.plugins.stock.commons.validator.annotation.ValidId;
import fr.paris.lutece.plugins.stock.utils.DateUtils;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * DTO for Seance
 */
public class SeanceDTO extends AbstractDTO<Offer>
{
    public static final String ATTR_DATE = "date";
    public static final String ATTR_REDUCT_PRICE = "reductPrice";
    public static final String ATTR_HOUR = "hour";
    public static final String ATTR_INIT_QUANTITY = "initialQuantity";
    public static final String ATTR_ID_CONTACT = "idContact";
    public static final String ATTR_MIN_TICKETS = "minTickets";
    public static final String ATTR_MAX_TICKETS = "maxTickets";
    private Integer id;
    private String description;
    private String name;
    @Min( value = 1 )
    private Float reductPrice;
    @ValidId
    private ShowDTO product = new ShowDTO(  );
    @Min( value = 1, message = "le type est obligatoire" )
    private Integer idGenre;
    private String typeName;
    @Min( value = 0 )
    @NotNull
    private Integer quantity;
    private Integer initialQuantity;
    @DateFormat( format = "dd/MM/yyyy" )
    @NotEmpty
    @AfterCurrentDate
    private String date;
    @DateFormat( format = "HH:mm" )
    @NotEmpty
    private String hour;
    private String statut;
    private Date dateHour;
    private Integer[] idContact;
    @Range( min = 1, max = 99, message = "#i18n{module.stock.tickets.validation.seanceDTO.minTickets.range}" )
    @NotNull
    private Integer minTickets;
    @Range( min = 1, max = 99, message = "#i18n{module.stock.tickets.validation.seanceDTO.maxTickets.range}" )
    @NotNull
    private Integer maxTickets;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getId(  )
    {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param idOffer the new id
     */
    public void setId( Integer idOffer )
    {
        this.id = idOffer;
    }

    /**
     * @return the description
     */
    public String getDescription(  )
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
     * @return the name
     */
    public String getName(  )
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
     * @return the reductPrice
     */
    public Float getReductPrice(  )
    {
        return reductPrice;
    }

    /**
     * Sets the reduct price.
     *
     * @param reductPrice the reductPrice to set
     */
    public void setReductPrice( Float reductPrice )
    {
        this.reductPrice = reductPrice;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity(  )
    {
        return quantity;
    }

    /**
     * Sets the quantity.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity( Integer quantity )
    {
        this.quantity = quantity;
    }

    /**
     * @return the initial quantity
     */
    public Integer getInitialQuantity(  )
    {
        return initialQuantity;
    }

    /**
     * Sets the initial quantity.
     *
     * @param initialQuantity the quantity to set
     */
    public void setInitialQuantity( Integer initialQuantity )
    {
        this.initialQuantity = initialQuantity;
    }

    /**
     * @return the hour
     */
    public String getHour(  )
    {
        return hour;
    }

    /**
     * Sets the hour.
     *
     * @param hour the hour to set
     */
    public void setHour( String hour )
    {
        this.hour = hour;
    }

    /**
     * @return the date
     */
    public String getDate(  )
    {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the date to set
     */
    public void setDate( String date )
    {
        this.date = date;
    }

    /**
     * Convert entity list from database
     *
     * @param listSource the list source
     * @return the result list
     */
    public static ResultList<SeanceDTO> convertEntityList( Collection<Offer> listSource )
    {
        ResultList<SeanceDTO> listDest = new ResultList<SeanceDTO>(  );

        if ( listSource instanceof ResultList )
        {
            listDest.setTotalResult( ( (ResultList<Offer>) listSource ).getTotalResult(  ) );
        }

        SeanceDTO seanceDTO;

        for ( Offer source : listSource )
        {
            seanceDTO = convertEntity( source );
            // Add date + hour merged
            seanceDTO.setDateHour( DateUtils.mergeDateHour( source.getAttributeDateMap(  ).get( SeanceDTO.ATTR_DATE ),
                    source.getAttributeDateMap(  ).get( SeanceDTO.ATTR_HOUR ) ) );
            listDest.add( seanceDTO );
        }

        return listDest;
    }

    /**
     * Convert entity from database
     *
     * @param source the source
     * @return the seance dto
     */
    public static SeanceDTO convertEntity( Offer source )
    {
        Mapper mapper = (Mapper) SpringContextService.getBean( "mapper" );
        SeanceDTO seance = mapper.map( source, SeanceDTO.class );

        Map<String, Timestamp> attributeDateList = source.getAttributeDateMap(  );

        if ( attributeDateList != null )
        {
            Timestamp date = attributeDateList.get( ATTR_DATE );

            if ( date != null )
            {
                seance.setDate( DateUtils.getDateFr( date ) );
            }

            Timestamp hour = attributeDateList.get( ATTR_HOUR );

            if ( hour != null )
            {
                seance.setHour( DateUtils.getHourFr( hour ) );
            }

            seance.setDateHour( DateUtils.mergeDateHour( date, hour ) );
        }

        Map<String, BigDecimal> attributeNumList = source.getAttributeNumMap(  );

        if ( attributeNumList != null )
        {
            if ( attributeNumList.get( ATTR_REDUCT_PRICE ) != null )
            {
                seance.setReductPrice( attributeNumList.get( ATTR_REDUCT_PRICE ).floatValue(  ) );
            }

            if ( attributeNumList.get( ATTR_INIT_QUANTITY ) != null )
            {
                seance.setInitialQuantity( attributeNumList.get( ATTR_INIT_QUANTITY ).intValue(  ) );
            }

            int nNbContacts = 0;
            List<Integer> listIdContact = new ArrayList<Integer>(  );
            BigDecimal idContact = attributeNumList.get( ATTR_ID_CONTACT );

            while ( idContact != null )
            {
                listIdContact.add( idContact.intValue(  ) );
                nNbContacts++;
                idContact = attributeNumList.get( ATTR_ID_CONTACT + nNbContacts );
            }

            Integer[] arrayIdContact = new Integer[nNbContacts];
            nNbContacts = 0;

            for ( Integer nIdContact : listIdContact )
            {
                arrayIdContact[nNbContacts] = nIdContact;
                nNbContacts++;
            }

            seance.setIdContact( arrayIdContact );
            
            if ( attributeNumList.get( ATTR_MIN_TICKETS ) != null )
            {
                seance.setMinTickets( attributeNumList.get( ATTR_MIN_TICKETS ).intValue(  ) );
            }
            
            if ( attributeNumList.get( ATTR_MAX_TICKETS ) != null )
            {
                seance.setMaxTickets( attributeNumList.get( ATTR_MAX_TICKETS ).intValue(  ) );
            }
        }

        return seance;
    }

    /**
     * Convert an offer to database
     * @see fr.paris.lutece.plugins.stock.commons.AbstractDTO#convert()
     */
    @Override
    public Offer convert(  )
    {
        Offer offer = mapper.map( this, Offer.class );

        if ( StringUtils.isNotEmpty( this.getDate(  ) ) )
        {
            offer.getAttributeDateList(  )
                 .add( new OfferAttributeDate( ATTR_DATE, DateUtils.getDate( this.getDate(  ), false ), offer ) );
        }

        if ( StringUtils.isNotEmpty( this.getHour(  ) ) )
        {
            offer.getAttributeDateList(  )
                 .add( new OfferAttributeDate( ATTR_HOUR, DateUtils.getHour( this.getHour(  ) ), offer ) );
        }

        if ( this.getReductPrice(  ) != null )
        {
            offer.getAttributeNumList(  )
                 .add( new OfferAttributeNum( ATTR_REDUCT_PRICE, BigDecimal.valueOf( this.getReductPrice(  ) ), offer ) );
        }

        if ( this.initialQuantity != null )
        {
            offer.getAttributeNumList(  )
                 .add( new OfferAttributeNum( ATTR_INIT_QUANTITY, BigDecimal.valueOf( this.getInitialQuantity(  ) ),
                    offer ) );
        }

        if ( ( this.idContact != null ) && ( this.idContact.length > 0 ) )
        {
            int nNbContact = 0;

            for ( Integer nIdContact : idContact )
            {
                offer.getAttributeNumList(  )
                     .add( new OfferAttributeNum( ( nNbContact == 0 ) ? ATTR_ID_CONTACT : ( ATTR_ID_CONTACT +
                        nNbContact ), BigDecimal.valueOf( nIdContact ), offer ) );
                nNbContact++;
            }
        }
        
        if ( this.minTickets != null )
        {
            offer.getAttributeNumList(  )
                 .add( new OfferAttributeNum( ATTR_MIN_TICKETS, BigDecimal.valueOf( this.getMinTickets(  ) ),
                    offer ) );
        }

        if ( this.maxTickets != null )
        {
            offer.getAttributeNumList(  )
                 .add( new OfferAttributeNum( ATTR_MAX_TICKETS, BigDecimal.valueOf( this.getMaxTickets(  ) ),
                    offer ) );
        }
        return offer;
    }

    /**
     * set the product to product
     * @param theProduct the product
     */
    public void setProduct( ShowDTO theProduct )
    {
        this.product = theProduct;
    }

    /**
     * return the product
     * @return the product
     */
    public ShowDTO getProduct(  )
    {
        return product;
    }

    /**
     * Sets the id genre.
     *
     * @param idGenre the new id genre
     */
    public void setIdGenre( Integer idGenre )
    {
        this.idGenre = idGenre;
    }

    /**
     * Gets the id genre.
     *
     * @return the id genre
     */
    public Integer getIdGenre(  )
    {
        return idGenre;
    }

    /**
     * Sets the type name.
     *
     * @param typeName the new type name
     */
    public void setTypeName( String typeName )
    {
        this.typeName = typeName;
    }

    /**
     * Gets the type name.
     *
     * @return the type name
     */
    public String getTypeName(  )
    {
        return typeName;
    }

    /**
     * Sets the statut.
     *
     * @param statut the statut to set
     */
    public void setStatut( String statut )
    {
        this.statut = statut;
    }

    /**
     * @return the statut
     */
    public String getStatut(  )
    {
        return statut;
    }

    /**
     * @return the dateHour
     */
    public Date getDateHour(  )
    {
        return dateHour;
    }

    /**
     * Sets the date hour.
     *
     * @param theDateHour the dateHour to set
     */
    public void setDateHour( Date theDateHour )
    {
        this.dateHour = theDateHour;
    }

    /**
     * Set the contact
     * @param idContact the new contact to set
     */
    public void setIdContact( Integer[] idContact )
    {
        this.idContact = idContact;
    }

    /**
     *
     * @return the contact
     */
    public Integer[] getIdContact(  )
    {
        return idContact;
    }

    /**
     * @return the minTickets
     */
    public Integer getMinTickets(  )
    {
        return minTickets;
    }

    /**
     * Sets the minTickets.
     *
     * @param minTickets the minTickets to set
     */
    public void setMinTickets( Integer minTickets )
    {
        this.minTickets = minTickets;
    }

    /**
     * @return the maxTickets
     */
    public Integer getMaxTickets(  )
    {
        return maxTickets;
    }

    /**
     * Sets the maxTickets.
     *
     * @param maxTickets the maxTickets to set
     */
    public void setMaxTickets( Integer maxTickets )
    {
        this.maxTickets = maxTickets;
    }
}
