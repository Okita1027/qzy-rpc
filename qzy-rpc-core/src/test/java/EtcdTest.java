import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.watch.WatchEvent;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author qzy
 * @time 2025年1月05日 9:22 星期日
 * @title
 */
public class EtcdTest {
    @Test
    public void test() throws ExecutionException, InterruptedException {
        Client client = Client.builder().endpoints("http://localhost:2379").build();
        GetResponse response = client.getKVClient().get(ByteSequence.from("name".getBytes())).get();
        response.getKvs().forEach(kv -> {
            System.out.println("key = " + kv.getKey());
            System.out.println("value = " + kv.getValue());
        });

        client.close();
    }

    @Test
    public void quickStart() throws ExecutionException, InterruptedException {
        // create client using endpoints
        Client client = Client.builder().endpoints("http://localhost:2379").build();

        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());

        // put the key-value
        kvClient.put(key, value).get();

        // get the CompletableFuture
        CompletableFuture<GetResponse> getFuture = kvClient.get(key);

        // get the value from CompletableFuture
        GetResponse response = getFuture.get();
        System.out.println("response = " + response);
        response.getKvs().forEach(kv -> {
            System.out.println("key = " + kv.getKey());
            System.out.println("value = " + kv.getValue());
        });

        // delete the key
        kvClient.delete(key).get();

        client.close();
    }

    @Test
    public void putGetTest() throws ExecutionException, InterruptedException {
        Client client = Client.builder().endpoints("http://localhost:2379").build();

        // 构建键值对
        ByteSequence key = ByteSequence.from("name".getBytes());
        ByteSequence value = ByteSequence.from("qzy".getBytes());

        // Put操作
        client.getKVClient().put(key, value).get();

        // Get操作
        GetResponse response = client.getKVClient().get(key).get();
        response.getKvs().forEach(kv -> {
            System.out.println("Key:" + kv.getKey().toString(StandardCharsets.UTF_8) + ",Value:" + kv.getValue().toString(StandardCharsets.UTF_8));
        });

        client.close();
    }

    @Test
    public void deleteTest() throws ExecutionException, InterruptedException {
        Client client = Client.builder().endpoints("http://localhost:2379").build();
        KV kvClient = client.getKVClient();

        ByteSequence key = ByteSequence.from("name".getBytes());

        // 删除键
        DeleteResponse deleteResponse = kvClient.delete(key).get();
        long deleted = deleteResponse.getDeleted();
        if (deleted > 0) {
            System.out.println("键值对已删除");
        } else {
            System.out.println("键值对不存在");
        }

        client.close();
    }


    @Test
    public void watchTest() throws ExecutionException, InterruptedException {
        Client client = Client.builder().endpoints("http://localhost:2379").build();

        ByteSequence key = ByteSequence.from("name".getBytes());

        // 启动监听
        client.getWatchClient().watch(key, watchResponse -> {
            watchResponse.getEvents().forEach(event -> {
                WatchEvent.EventType eventType = event.getEventType();
                String value = event.getKeyValue().getValue().toString();
                System.out.println("eventType = " + eventType + ",value = " + value);
            });
        });

        System.out.println("Watching key changes...");

        client.getKVClient().put(key, ByteSequence.from("admin".getBytes())).get();

        client.close();
    }

    @Test
    public void leaseTest() throws ExecutionException, InterruptedException {
        Client client = Client.builder().endpoints("http://localhost:2379").build();

        // 创建租约，设置存活时间为5秒
        long leaseId = client.getLeaseClient().grant(30).get().getID();
        System.out.println("leaseId = " + leaseId);

        // 将键值绑定到租约
        ByteSequence key = ByteSequence.from("name".getBytes());
        ByteSequence value = ByteSequence.from("qzy".getBytes());
        PutOption putOption = PutOption.newBuilder().withLeaseId(leaseId).build();
        client.getKVClient().put(key, value, putOption).get();
        System.out.println("Temporary key-value pair added with lease ID:" + leaseId);

        client.close();
    }
}
