package org.javamoney.moneta.function;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.money.CurrencyUnit;

/**
 * This statisticsMap is decorator of HashMap that returns an empty Summary when there
 * isn't currency in get's method
 *
 * @author otaviojava
 */
class MonetarySummaryMap implements
        Map<CurrencyUnit, MonetarySummaryStatistics> {

    private final Map<CurrencyUnit, MonetarySummaryStatistics> statisticsMap = new HashMap<>();

    @Override
    public int size() {
        return statisticsMap.size();
    }

    @Override
    public boolean isEmpty() {
        return statisticsMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return statisticsMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return statisticsMap.containsValue(value);
    }

    @Override
    public MonetarySummaryStatistics get(Object key) {
        if (CurrencyUnit.class.isInstance(key)) {
            CurrencyUnit unit = CurrencyUnit.class.cast(key);
            return statisticsMap.getOrDefault(unit, new DefaultMonetarySummaryStatistics(
                    unit));
        }
        return statisticsMap.get(key);
    }

    @Override
    public MonetarySummaryStatistics put(CurrencyUnit key,
                                         MonetarySummaryStatistics value) {
        return statisticsMap.put(key, value);
    }

    @Override
    public MonetarySummaryStatistics remove(Object key) {
        return statisticsMap.remove(key);
    }

    @Override
    public void putAll(
            Map<? extends CurrencyUnit, ? extends MonetarySummaryStatistics> m) {
        statisticsMap.putAll(m);
    }

    @Override
    public void clear() {
        statisticsMap.clear();
    }

    @Override
    public Set<CurrencyUnit> keySet() {
        return statisticsMap.keySet();
    }

    @Override
    public Collection<MonetarySummaryStatistics> values() {
        return statisticsMap.values();
    }

    @Override
    public Set<java.util.Map.Entry<CurrencyUnit, MonetarySummaryStatistics>> entrySet() {
        return statisticsMap.entrySet();
    }

    @Override
    public boolean equals(Object obj) {
        if (MonetarySummaryMap.class.isInstance(obj)) {
            MonetarySummaryMap other = MonetarySummaryMap.class.cast(obj);
            return statisticsMap.equals(other.statisticsMap);
        }
        return false;
    }

    @Override
    public MonetarySummaryStatistics putIfAbsent(CurrencyUnit key,
                                                 MonetarySummaryStatistics value) {
        MonetarySummaryStatistics v = statisticsMap.get(key);
        if (Objects.isNull(v)) {
            v = put(key, value);
        }
        return v;
    }

    @Override
    public int hashCode() {
        return statisticsMap.hashCode();
    }

    @Override
    public String toString() {
        return "MonetarySummaryMap: " + statisticsMap.toString();
    }
}
