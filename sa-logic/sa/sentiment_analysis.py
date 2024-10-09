from confluent_kafka import Consumer, Producer
import json
import os

def create_kafka_consumer():
    consumer_conf = {
        'bootstrap.servers': os.getenv('KAFKA_BROKER'),
        'group.id': 'logic-group',
        'auto.offset.reset': 'earliest'
    }
    consumer = Consumer(consumer_conf)
    consumer.subscribe(['webapp_to_logic'])
    return consumer

def create_kafka_producer():
    producer_conf = {
        'bootstrap.servers': os.getenv('KAFKA_BROKER'),
    }
    return Producer(producer_conf)

def analyze_sentiment(text):
    # Simple sentiment analysis logic (you can replace with actual logic)
    return "Positive" if "good" in text else "Negative"

def main():
    consumer = create_kafka_consumer()
    producer = create_kafka_producer()

    while True:
        msg = consumer.poll(1.0)
        if msg is None:
            continue
        if msg.error():
            print(f"Error: {msg.error()}")
            continue

        request = msg.value().decode('utf-8')
        result = analyze_sentiment(request)
        print(f"Sentiment analysis result: {result}")

        producer.produce('logic_to_webapp', value=result.encode('utf-8'))
        producer.flush()

if __name__ == "__main__":
    main()

