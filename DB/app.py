from flask import Flask, request, jsonify
from flask_cors import CORS
import sqlite3
import uuid

app = Flask(__name__)
CORS(app, origins=["https://67d1941616149004c567da86--gcoin3.netlify.app"])

def get_db_connection():
    conn = sqlite3.connect("users.db", check_same_thread=False)
    conn.row_factory = sqlite3.Row
    return conn

@app.teardown_appcontext
def close_connection(exception):
    conn = get_db_connection()
    conn.close()

# Database setup
conn = get_db_connection()
cursor = conn.cursor()
cursor.execute('''CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id TEXT UNIQUE,
    wallet TEXT UNIQUE,
    stake_amount REAL,
    transaction_id TEXT UNIQUE,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)''')
conn.commit()
conn.close()

@app.route('/check_registration', methods=['POST'])
def check_registration():
    conn = get_db_connection()
    cursor = conn.cursor()
    data = request.json
    wallet = data['wallet']

    cursor.execute("SELECT user_id FROM users WHERE wallet = ?", (wallet,))
    user = cursor.fetchone()
    conn.close()

    if user:
        return jsonify({"registered": True, "user_id": user['user_id']})
    else:
        return jsonify({"registered": False})

@app.route('/register', methods=['POST'])
def register():
    conn = get_db_connection()
    cursor = conn.cursor()
    data = request.json
    wallet = data['wallet']
    stake = float(data['stake'])
    transaction_id = data['txHash']

    user_id = str(uuid.uuid4())[:8]

    try:
        cursor.execute("INSERT INTO users (user_id, wallet, stake_amount, transaction_id) VALUES (?, ?, ?, ?)",
                       (user_id, wallet, stake, transaction_id))
        conn.commit()
        conn.close()
        print(f"Generated User ID: {user_id}")
        return jsonify({"message": "Registered Successfully!", "user_id": user_id})
    except sqlite3.IntegrityError:
        conn.close()
        return jsonify({"error": "Wallet already registered"}), 400

@app.route('/unregister', methods=['POST'])
def unregister():
    conn = get_db_connection()
    cursor = conn.cursor()
    data = request.json
    wallet = data['wallet']

    cursor.execute("SELECT stake_amount FROM users WHERE wallet = ?", (wallet,))
    user = cursor.fetchone()
    if not user:
        conn.close()
        return jsonify({"error": "User not found"}), 404

    stake_amount = user['stake_amount']

    # Delete user entry
    cursor.execute("DELETE FROM users WHERE wallet = ?", (wallet,))
    conn.commit()
    conn.close()

    return jsonify({"message": "Unregistered Successfully! Staked SOL will be returned."})

def display_data():
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM users")
    rows = cursor.fetchall()
    conn.close()

    print("id | user_id | wallet | stake_amount | transaction_id | registered_at")
    print("--------------------------------------------------------------")
    for row in rows:
        print(f"{row['id']} | {row['user_id']} | {row['wallet']} | {row['stake_amount']} | {row['transaction_id']} | {row['registered_at']}")

# Call the function to display data
display_data()

if __name__ == '__main__':
    app.run(debug=True)