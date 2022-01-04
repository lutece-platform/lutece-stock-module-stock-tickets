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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * This class provides Data Access methods for Person objects
 */
public final class PurchaseStatisticDAO extends AbstractStockDAO<Integer, PurchaseStatistic> implements IPurchaseStatisticDAO
{
    private static final String SELECT_COUNT_STATISTICS_PURCHASES = "SELECT count(distinct purchase_statistic.purchase_id_purchase) AS compteur, purchase_statistic.";
    private static final String END_SELECT_COUNT_STATISTICS_PURCHASES = ",purchase_statistic.year FROM stock_ticket_purchase_statistic AS purchase_statistic";
    private static final String SELECT_COUNT_STATISTIC_PURCHASES_DEFAULT = "SELECT count( distinct purchase_statistic.purchase_id_purchase) FROM stock_ticket_purchase_statistic AS purchase_statistic";
    private static final String WHERE_PURCHASE_DATE_GREATER_THAN = " WHERE purchase_statistic.date >= CAST('";
    private static final String WHERE_PURCHASE_DATE_LESS_THAN = " purchase_statistic.date <= CAST('";
    private static final String PURCHASE_STATISTIC_YEAR = ", purchase_statistic.year";
    private static final String START_OF_DAY = " 00:00:00' AS DATETIME)";
    private static final String END_OF_DAY = " 23:59:59' AS DATETIME)";
    private static final String GROUP_BY_PURCHASE_STATISTIC = " GROUP BY purchase_statistic.";
    private static final String WHERE_OPERATOR = " WHERE";
    private static final String AND_OPERATOR = " AND";
    private static final String DATE_OF_YEAR_PARAMETER = "dayOfYear";
    private static final String WEEK_PARAMETER = "week";
    private static final String MONTH_PARAMETER = "month";
    
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
    public List<PurchaseStatistic> getAllByIdPurchase( Integer idPurchase )
    {
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );

        CriteriaQuery<PurchaseStatistic> cq = cb.createQuery( PurchaseStatistic.class );

        Root<PurchaseStatistic> root = cq.from( PurchaseStatistic.class );

        // predicates list
        List<Predicate> listPredicates = new ArrayList<>( );

        if ( idPurchase != null )
        {
            listPredicates.add( cb.equal( root.get( PurchaseStatistic_.purchase ), idPurchase ) );
        }

        if ( !listPredicates.isEmpty( ) )
        {
            // add existing predicates to Where clause
            cq.where( listPredicates.toArray( new Predicate [ 0] ) );
        }

        cq.distinct( true );

        TypedQuery<PurchaseStatistic> query = em.createQuery( cq );

        return query.getResultList( );
    }

    /**
     * {@inheritDoc}
     */
    public List<ResultStatistic> getAllResultStatisticByParameters( String strTimesUnit, String strDateDebut, String strDateFin )
    {
        StringBuilder requeteSQL = new StringBuilder( );

        requeteSQL.append( SELECT_COUNT_STATISTICS_PURCHASES );

        if ( strTimesUnit.equals( "0" ) )
        {
            requeteSQL.append( DATE_OF_YEAR_PARAMETER );
        }
        else
            if ( strTimesUnit.equals( "1" ) )
            {
                requeteSQL.append( WEEK_PARAMETER );
            }
            else
            {
                requeteSQL.append( MONTH_PARAMETER );
            }

        requeteSQL.append( END_SELECT_COUNT_STATISTICS_PURCHASES );

        boolean isFirstCondition = Boolean.TRUE;

        if ( ( strDateDebut != null ) && !strDateDebut.equals( "" ) )
        {
            requeteSQL.append( WHERE_PURCHASE_DATE_GREATER_THAN + strDateDebut + START_OF_DAY );
            isFirstCondition = Boolean.FALSE;
        }

        if ( ( strDateFin != null ) && !strDateFin.equals( "" ) )
        {
            if ( isFirstCondition )
            {
                requeteSQL.append( WHERE_OPERATOR );
            }
            else
            {
                requeteSQL.append( AND_OPERATOR );
            }

            requeteSQL.append( WHERE_PURCHASE_DATE_LESS_THAN + strDateFin + END_OF_DAY );
        }

        requeteSQL.append( GROUP_BY_PURCHASE_STATISTIC );

        if ( strTimesUnit.equals( "0" ) )
        {
            requeteSQL.append( DATE_OF_YEAR_PARAMETER );
        }
        else
            if ( strTimesUnit.equals( "1" ) )
            {
                requeteSQL.append( WEEK_PARAMETER );
            }
            else
            {
                requeteSQL.append( MONTH_PARAMETER );
            }

        requeteSQL.append( PURCHASE_STATISTIC_YEAR );

        Query query = getEM( ).createNativeQuery( requeteSQL.toString( ) );

        List<Object> listeResultat = query.getResultList( );

        List<ResultStatistic> listeResultStatistic = new ArrayList<>( );

        if ( !listeResultat.isEmpty( ) )
        {
            for ( Object ligneResultat : listeResultat )
            {
                Object [ ] listeAttributs = (Object [ ]) ligneResultat;

                if ( ( listeAttributs [0] != null ) && ( listeAttributs [1] != null ) && ( listeAttributs [2] != null ) )
                {
                    ResultStatistic resultStatistic = new ResultStatistic( );
                    resultStatistic.setNumberResponse( Integer.decode( listeAttributs [0].toString( ) ) );

                    Calendar calendar = new GregorianCalendar( );

                    int nTimesUnit;

                    if ( strTimesUnit.equals( "0" ) )
                    {
                        nTimesUnit = Calendar.DAY_OF_YEAR;
                    }
                    else
                        if ( strTimesUnit.equals( "1" ) )
                        {
                            nTimesUnit = Calendar.WEEK_OF_YEAR;
                        }
                        else
                        {
                            nTimesUnit = Calendar.MONTH;
                        }

                    calendar.set( nTimesUnit, Integer.decode( listeAttributs [1].toString( ) ) );
                    calendar.set( Calendar.YEAR, Integer.decode( listeAttributs [2].toString( ) ) );
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
    public Integer getCountPurchasesByDates( String strDateDebut, String strDateFin )
    {
        Integer result = 0;
        StringBuilder requeteSQL = new StringBuilder( );

        requeteSQL.append( SELECT_COUNT_STATISTIC_PURCHASES_DEFAULT );

        boolean isFirstCondition = Boolean.TRUE;

        if ( ( strDateDebut != null ) && !strDateDebut.equals( "" ) )
        {
            requeteSQL.append( WHERE_PURCHASE_DATE_GREATER_THAN + strDateDebut + START_OF_DAY );
            isFirstCondition = Boolean.FALSE;
        }

        if ( ( strDateFin != null ) && !strDateFin.equals( "" ) )
        {
            if ( isFirstCondition )
            {
                requeteSQL.append( WHERE_OPERATOR );
            }
            else
            {
                requeteSQL.append( AND_OPERATOR );
            }

            requeteSQL.append( WHERE_PURCHASE_DATE_LESS_THAN + strDateFin + END_OF_DAY );
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
