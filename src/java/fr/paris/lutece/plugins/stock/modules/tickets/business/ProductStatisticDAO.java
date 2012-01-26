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
package fr.paris.lutece.plugins.stock.modules.tickets.business;

import fr.paris.lutece.plugins.stock.business.product.Product;
import fr.paris.lutece.plugins.stock.business.product.Product_;
import fr.paris.lutece.plugins.stock.commons.dao.AbstractStockDAO;
import fr.paris.lutece.plugins.stock.modules.tickets.service.TicketsPlugin;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * This class provides Data Access methods for Person objects
 */
public final class ProductStatisticDAO extends AbstractStockDAO<Integer, ProductStatistic>
    implements IProductStatisticDAO
{

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public String getPluginName( )
    {
        return TicketsPlugin.PLUGIN_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public List<ProductStatistic> getAllByIdProduct( Integer idProduct )
    {
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );

        CriteriaQuery<ProductStatistic> cq = cb.createQuery( ProductStatistic.class );

        Root<ProductStatistic> root = cq.from( ProductStatistic.class );
        // predicates list
        List<Predicate> listPredicates = new ArrayList<Predicate>( );

        Join<ProductStatistic, Product> product = root.join( ProductStatistic_.product, JoinType.INNER );
        
        if ( idProduct != null )
        {
            listPredicates.add( cb.equal( product.get( Product_.id ), idProduct ) );
        }

        if ( !listPredicates.isEmpty( ) )
        {
            // add existing predicates to Where clause
            cq.where( listPredicates.toArray( new Predicate[0] ) );
        }
        //buildSortQuery( filter, root, cq, cb );
        cq.distinct( true );

        TypedQuery<ProductStatistic> query = em.createQuery( cq );

        return query.getResultList( );
    }

    /**
     * {@inheritDoc}
     */
    public List<ResultStatistic> getAllResultStatisticByParameters( String strTimesUnit, String strDateDebut,
            String strDateFin )
    {
        StringBuffer requeteSQL = new StringBuffer( );

        requeteSQL
                .append( "SELECT count(distinct product_statistic.product_id_product) AS compteur, product_statistic." );
        if ( strTimesUnit.equals( "0" ) )
        {
            requeteSQL.append( "dayOfYear" );
        }
        else if ( strTimesUnit.equals( "1" ) )
        {
            requeteSQL.append( "week" );
        }
        else
        {
            requeteSQL.append( "month" );
        }

        requeteSQL.append( ",product_statistic.year FROM stock_ticket_product_statistic AS product_statistic" );
        Boolean isFirstCondition = Boolean.TRUE;
        if ( strDateDebut != null && !strDateDebut.equals( "" ) )
        {
            requeteSQL.append( " WHERE product_statistic.date >= CAST('" + strDateDebut + " 00:00:00' AS DATETIME)" );
            isFirstCondition = Boolean.FALSE;
        }
        if ( strDateFin != null && !strDateFin.equals( "" ) )
        {
            if ( isFirstCondition )
            {
                requeteSQL.append( " WHERE" );
            }
            else
            {
                requeteSQL.append( " AND" );
            }
            requeteSQL.append( " product_statistic.date <= CAST('" + strDateFin + " 23:59:59' AS DATETIME)" );
        }

        requeteSQL.append( " GROUP BY product_statistic." );
        if ( strTimesUnit.equals( "0" ) )
        {
            requeteSQL.append( "dayOfYear" );
        }
        else if ( strTimesUnit.equals( "1" ) )
        {
            requeteSQL.append( "week" );
        }
        else
        {
            requeteSQL.append( "month" );
        }
        
        requeteSQL.append( ", product_statistic.year" );

        Query query = getEM( ).createNativeQuery( requeteSQL.toString( ) );
        
        List<Object> listeResultat = query.getResultList(  );

        List<ResultStatistic> listeResultStatistic = new ArrayList<ResultStatistic>( );

        if ( listeResultat.size( ) > 0 )
        {
            for( Object ligneResultat : listeResultat )
            {
                Object[] listeAttributs = ( Object[] ) ligneResultat;
                if ( listeAttributs[0] != null && listeAttributs[1] != null && listeAttributs[2] != null )
                {
                    ResultStatistic resultStatistic = new ResultStatistic( );
                    resultStatistic.setNumberResponse( Integer.decode( listeAttributs[0].toString( ) ) );
                    Calendar calendar = new GregorianCalendar( );

                    int nTimesUnit;

                    if ( strTimesUnit.equals( "0" ) )
                    {
                        nTimesUnit = Calendar.DAY_OF_YEAR;
                    }
                    else if ( strTimesUnit.equals( "1" ) )
                    {
                        nTimesUnit = Calendar.WEEK_OF_YEAR;
                    }
                    else
                    {
                        nTimesUnit = Calendar.MONTH;
                    }

                    calendar.set( nTimesUnit, Integer.decode( listeAttributs[1].toString( ) ) );
                    calendar.set( Calendar.YEAR, Integer.decode( listeAttributs[2].toString( ) ) );
                    resultStatistic.setStatisticDate( new Timestamp( calendar.getTimeInMillis( ) ) );

                    listeResultStatistic.add( resultStatistic );
                }
            }
            }

        return listeResultStatistic;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getCountProductsByDates( String strDateDebut, String strDateFin )
    {
        Integer result = 0;
        StringBuffer requeteSQL = new StringBuffer( );

        requeteSQL.append( "SELECT count( distinct product_statistic.product_id_product)  " );
        requeteSQL.append( " FROM stock_ticket_product_statistic AS product_statistic" );

        Boolean isFirstCondition = Boolean.TRUE;
        if ( strDateDebut != null && !strDateDebut.equals( "" ) )
        {
            requeteSQL.append( " WHERE product_statistic.date >= CAST('" + strDateDebut + " 00:00:00' AS DATETIME)" );
            isFirstCondition = Boolean.FALSE;
        }
        if ( strDateFin != null && !strDateFin.equals( "" ) )
        {
            if ( isFirstCondition )
            {
                requeteSQL.append( " WHERE" );
            }
            else
            {
                requeteSQL.append( " AND" );
            }
            requeteSQL.append( " product_statistic.date <= CAST('" + strDateFin + " 23:59:59' AS DATETIME)" );
        }

        Query query = getEM( ).createNativeQuery( requeteSQL.toString( ) );
        List<Object> listeCount = query.getResultList( );

        if ( listeCount.size( ) == 1 )
        {
            Object obj = listeCount.get( 0 );
            if ( obj != null )
            {
                BigInteger bigInt = (BigInteger) obj;
                result = bigInt.intValue( );
            }
        }
        return result;
    }
}
