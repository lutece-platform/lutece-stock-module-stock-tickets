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

import fr.paris.lutece.plugins.stock.business.attribute.offer.OfferAttributeDate;
import fr.paris.lutece.plugins.stock.business.attribute.purchase.PurchaseAttributeDate;
import fr.paris.lutece.plugins.stock.business.attribute.utils.AttributeDateUtils;
import fr.paris.lutece.plugins.stock.business.offer.Offer;
import fr.paris.lutece.plugins.stock.business.offer.OfferGenre;
import fr.paris.lutece.plugins.stock.business.offer.OfferGenre_;
import fr.paris.lutece.plugins.stock.business.offer.Offer_;
import fr.paris.lutece.plugins.stock.business.product.Product;
import fr.paris.lutece.plugins.stock.business.product.Product_;
import fr.paris.lutece.plugins.stock.business.purchase.Purchase;
import fr.paris.lutece.plugins.stock.business.purchase.PurchaseDAO;
import fr.paris.lutece.plugins.stock.business.purchase.PurchaseFilter;
import fr.paris.lutece.plugins.stock.business.purchase.Purchase_;
import fr.paris.lutece.plugins.stock.utils.DateUtils;
import fr.paris.lutece.plugins.stock.utils.NumberUtils;
import fr.paris.lutece.plugins.stock.utils.jpa.StockJPAUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;


/**
 * DOCUMENT ME!
 *
 * @author nmoitry
 */
public class ReservationDAO extends PurchaseDAO<Integer, Purchase> implements IReservationDAO
{

    /**
     * Build the criteria query used when purchases are searched by filter
     * @param filter the filter
     * @param root the purchase root
     * @param query the criteria query
     * @param builder the criteria builder
     */
    protected void buildCriteriaQuery( PurchaseFilter filter, Root<Purchase> root, CriteriaQuery<Purchase> query,
            CriteriaBuilder builder )
    {
        // predicates list
        List<Predicate> listPredicates = new ArrayList<Predicate>(  );
        
        Join<Purchase, Offer> offer = root.join( Purchase_.offer, JoinType.INNER );
        Join<Offer, Product> product = offer.join( Offer_.product, JoinType.INNER );
        Join<Offer, OfferGenre> type = offer.join( Offer_.type, JoinType.INNER );
        
        if ( StringUtils.isNotBlank( filter.getUserName(  ) ) )
        {
            listPredicates.add( builder.like( root.get( Purchase_.userName ),
                    StockJPAUtils.buildCriteriaLikeString( filter.getUserName(  ) ) ) );
        }
        
        if ( StringUtils.isNotBlank( filter.getProductName(  ) ) )
        {
            listPredicates.add( builder.like( product.get( Product_.name ),
                    StockJPAUtils.buildCriteriaLikeString( filter.getProductName(  ) ) ) );
        }

        if ( filter.getIdProduct( ) != null && filter.getIdProduct( ) > 0 )
        {
            listPredicates.add( builder.equal( product.get( Product_.id ), filter.getIdProduct( ) ) );
        }

        if ( StringUtils.isNotBlank( filter.getId( ) ) && NumberUtils.validateInt( filter.getId( ) ) )
        {
            listPredicates.add( builder.equal( root.get( Purchase_.id ), Integer.parseInt( filter.getId( ) ) ) );
        }
        
        if ( filter instanceof ReservationFilter )
        {
            ReservationFilter reservationFilter = (ReservationFilter) filter;
            // Date from (= date of reservation <= date from)
            if ( reservationFilter.getDateBegin( ) != null )
            {
                Timestamp dateFrom = DateUtils.getDate( reservationFilter.getDateBegin( ), false );
                Join<Purchase, PurchaseAttributeDate> join = root.join( Purchase_.attributeDateList );
                listPredicates.add( AttributeDateUtils.greaterThanOrEqualTo( builder, join, ReservationDTO.ATTR_DATE,
                        dateFrom ) );
            }
            // Date to (=date of reservation >= date to)
            if ( reservationFilter.getDateEnd( ) != null )
            {
                Timestamp dateEnd = DateUtils.getDate( reservationFilter.getDateEnd( ), false );
                Join<Purchase, PurchaseAttributeDate> join = root.join( Purchase_.attributeDateList );
                listPredicates
                        .add( AttributeDateUtils.lessThanOrEqualTo( builder, join, ReservationDTO.ATTR_DATE, dateEnd ) );
            }

            // Date the (=date reservation == date the)
            if ( reservationFilter.getDateOr( ) != null )
            {
                Timestamp dateThe = DateUtils.getDate( reservationFilter.getDateOr( ), false );
                Join<Purchase, PurchaseAttributeDate> join = root.join( Purchase_.attributeDateList );
                listPredicates.add( AttributeDateUtils.equal( builder, join, ReservationDTO.ATTR_DATE, dateThe ) );
            }
        }
        
        // Date from (= date of reservation <= date from)
        if ( filter.getDateBeginOffer( ) != null )
        {
            Timestamp dateFrom = DateUtils.getDate( filter.getDateBeginOffer( ), false );
            Join<Offer, OfferAttributeDate> join = offer.join( Offer_.attributeDateList );
            listPredicates.add( AttributeDateUtils.greaterThanOrEqualTo( builder, join, SeanceDTO.ATTR_DATE,
                    dateFrom ) );
        }
        // Date to (=date of reservation >= date to)
        if ( filter.getDateEndOffer( ) != null )
        {
            Timestamp dateEnd = DateUtils.getDate( filter.getDateEndOffer( ), false );
            Join<Offer, OfferAttributeDate> join = offer.join( Offer_.attributeDateList );
            listPredicates
                    .add( AttributeDateUtils.lessThanOrEqualTo( builder, join, SeanceDTO.ATTR_DATE, dateEnd ) );
        }
        
        if ( filter.getIdGenre(  ) != null && filter.getIdGenre(  ) > 0 )
        {
            listPredicates.add( builder.equal( type.get( OfferGenre_.id ),
            		filter.getIdGenre(  ) ) );
        }
        
        if ( filter.getIdOffer(  ) != null && filter.getIdOffer(  ) > 0 )
        {
            listPredicates.add( builder.equal( offer.get( Offer_.id ),
            		filter.getIdOffer(  ) ) );
        }

        if ( !listPredicates.isEmpty(  ) )
        {
            // add existing predicates to Where clause
            query.where( listPredicates.toArray( new Predicate[0] ) );
        }
    }
    
    /**
     * Add the order by parameter to the query
     * @param filter the filter
     * @param root the purchase root
     * @param query the criteria query
     * @param builder the criteria builder
     */
    protected void buildSortQuery( PurchaseFilter filter, Root<Purchase> root, CriteriaQuery<Purchase> query,
        CriteriaBuilder builder )
    {
        if ( filter.getOrders( ) != null && !filter.getOrders( ).isEmpty( ) )
        {
            List<Order> orderList = new ArrayList<Order>( );
            
            Join<Purchase, Offer> offer = root.join( Purchase_.offer, JoinType.INNER );
            Join<Offer, Product> product = offer.join( Offer_.product, JoinType.INNER );
            Join<Offer, OfferGenre> type = offer.join( Offer_.type, JoinType.INNER );

            if ( filter.isOrderAsc(  ) )
            {
                // get asc order
            	for ( String order : filter.getOrders( ) )
            	{
            		if ( order.equals( "offer.product.name" ) )
            		{
            			orderList.add( builder.asc( product.get( "name" ) ) );
            		}
            		else if ( order.equals( "date" ) )
            		{
                        Join<Purchase, PurchaseAttributeDate> joinDate = root.join( Purchase_.attributeDateList );
                        addRestriction( query, builder.equal( joinDate.get( "key" ), "date" ) );
                        orderList.add( builder.desc( joinDate.get( "value" ) ) );
            		}
            		else if ( order.equals( "offer.date" ) )
            		{
            			Join<Offer, OfferAttributeDate> joinDate = offer.join( Offer_.attributeDateList );
                        addRestriction( query, builder.equal( joinDate.get( "key" ), "date" ) );
            			orderList.add( builder.desc( joinDate.get( "value" ) ) );
            			Join<Offer, OfferAttributeDate> joinHour = offer.join( Offer_.attributeDateList );
                        addRestriction( query, builder.equal( joinHour.get( "key" ), "hour" ) );
            			orderList.add( builder.asc( joinHour.get( "value" ) ) );
            		}
            		else if ( order.equals( "offer.type.name" ) )
            		{
            			orderList.add( builder.asc( type.get( "name" ) ) );
            		}
            		else
            		{
            			orderList.add( builder.asc( root.get( order ) ) );
            		}
            	}
            }
            else
            {
                // get desc order
            	for ( String order : filter.getOrders( ) )
            	{
            		if ( order.equals( "offer.product.name" ) )
            		{
            			orderList.add( builder.desc( product.get( "name" ) ) );
            		}
                    else if ( order.equals( "date" ) )
                    {
                        Join<Purchase, PurchaseAttributeDate> joinDate = root.join( Purchase_.attributeDateList );
                        addRestriction( query, builder.equal( joinDate.get( "key" ), "date" ) );
                        orderList.add( builder.asc( joinDate.get( "value" ) ) );
                    }
                    else if ( order.equals( "offer.date" ) )
                    {
                        Join<Offer, OfferAttributeDate> joinDate = offer.join( Offer_.attributeDateList );
                        addRestriction( query, builder.equal( joinDate.get( "key" ), "date" ) );
                        orderList.add( builder.asc( joinDate.get( "value" ) ) );
                        Join<Offer, OfferAttributeDate> joinHour = offer.join( Offer_.attributeDateList );
                        addRestriction( query, builder.equal( joinHour.get( "key" ), "hour" ) );
                        orderList.add( builder.desc( joinHour.get( "value" ) ) );
                    }
            		else if ( order.equals( "offer.type.name" ) )
            		{
            			orderList.add( builder.desc( type.get( "name" ) ) );
            		}
            		else
            		{
            			orderList.add( builder.desc( root.get( order ) ) );
            		}
            	}
            }

            query.orderBy( orderList );
        }
    }

}
