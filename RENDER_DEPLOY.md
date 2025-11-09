# Quick Deploy to Render.com (Free Java Hosting)

## Why Render?
- ✅ **FREE tier** (no credit card required)
- ✅ Runs Java applications
- ✅ Custom domain support with free SSL
- ✅ Auto-deploy from GitHub
- ✅ No regional restrictions

---

## 🚀 Deployment Steps

### Step 1: Create render.yaml

This file tells Render how to run your app:

```yaml
services:
  - type: web
    name: fibonacci-curve
    env: java
    buildCommand: javac FibonacciCalculator.java SimpleHTTPServer.java
    startCommand: java SimpleHTTPServer 10000
    envVars:
      - key: PORT
        value: 10000
```

### Step 2: Push to GitHub

```powershell
cd "C:\Users\91936\Downloads\ZP_MAIN!"

# Add render.yaml
git add render.yaml
git commit -m "Add Render deployment config"
git push origin gh-pages
```

### Step 3: Deploy on Render

1. Go to https://render.com
2. Click **"Get Started"** (Sign up with GitHub)
3. Click **"New +"** → **"Web Service"**
4. Connect your GitHub repo: `saravanan9168/math`
5. Select branch: `gh-pages`
6. Render will auto-detect settings from render.yaml
7. Click **"Create Web Service"**

### Step 4: Get Your URL

After deployment (2-3 minutes), you'll get:
```
https://fibonacci-curve.onrender.com
```

### Step 5: Add Custom Domain

In Render dashboard:
1. Go to your service → **Settings** → **Custom Domain**
2. Add: `math.code-x.me`
3. Render will show you what DNS records to add

### Step 6: Update DNS in Namecheap

Replace the GitHub Pages CNAME with Render's:

```
Type:  CNAME
Host:  math
Value: [URL provided by Render]
TTL:   Automatic
```

---

## ✅ Done!

Your Java backend will run on Render and be accessible at:
- `https://math.code-x.me`

**Free tier includes:**
- 750 hours/month (plenty for a project)
- Auto-deploy from GitHub
- Free SSL certificate
- Custom domain

---

## 🔄 Alternative: Use Railway.app

If you prefer Railway:

1. Go to https://railway.app
2. Sign in with GitHub
3. Click **"New Project"** → **"Deploy from GitHub repo"**
4. Select: `saravanan9168/math`
5. Add build command: `javac *.java`
6. Add start command: `java SimpleHTTPServer $PORT`
7. Add custom domain: `math.code-x.me`

Railway gives you $5/month free credit (student-friendly!).

---

## 📝 Summary

**Render.com is your best bet because:**
- No credit card needed for free tier
- Works with student GitHub accounts
- Supports Java out of the box
- Free SSL for custom domains
- Better than Azure for students

Let me know which platform you prefer and I'll help you set it up! 🚀
