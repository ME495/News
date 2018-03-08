package team.innovation.news.entity;
/**
 * Created by ME495 on 2018/3/7.
 */

/**
 * 作者：程坚
 * 时间：2018/3/7
 * 描述：实体类，新闻频道
 */
public class Channel {
    private String channelId, name;

    public Channel(String channelId, String name) {
        this.channelId = channelId;
        this.name = name;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
