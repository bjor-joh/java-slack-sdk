package com.slack.api.scim.metrics;

import com.slack.api.rate_limits.metrics.impl.BaseRedisMetricsDatastore;
import com.slack.api.scim.SCIMApiResponse;
import com.slack.api.scim.impl.AsyncExecutionSupplier;
import com.slack.api.scim.impl.AsyncRateLimitQueue;
import redis.clients.jedis.JedisPool;

public class RedisMetricsDatastore extends BaseRedisMetricsDatastore<
        AsyncExecutionSupplier<? extends SCIMApiResponse>, AsyncRateLimitQueue.SCIMMessage> {

    public RedisMetricsDatastore(String appName, JedisPool jedisPool) {
        super(appName, jedisPool);
    }

    @Override
    public AsyncRateLimitQueue getRateLimitQueue(String executorName, String teamId) {
        return AsyncRateLimitQueue.get(executorName, teamId);
    }

}
