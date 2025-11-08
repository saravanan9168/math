# 🚀 Azure Deployment Guide - Fibonacci Curve Generator

## Your Website is Ready for Azure! ✅

Your `index.html` is a **static website** with no dependencies - perfect for Azure!

---

## 🌟 **Option 1: Azure Static Web Apps (BEST & FREE)**

### Why This Option?
- ✅ **FREE tier** (no credit card initially)
- ✅ Automatic HTTPS & Custom domain
- ✅ Global CDN for fast loading
- ✅ CI/CD with GitHub
- ✅ Perfect for HTML/JavaScript apps

### 📋 Prerequisites:
- Azure account (create free at https://azure.microsoft.com/free/)
- GitHub account
- Your code pushed to GitHub

---

### 🔧 **Step-by-Step Deployment:**

#### **Step 1: Push Your Code to GitHub**

```powershell
# Configure Git (if needed)
git config user.name "Your Name"
git config user.email "your.email@example.com"

# Add all files
cd "c:\Users\91936\OneDrive\Documents\ZP intern"
git add .
git commit -m "Deploy Fibonacci Curve Generator to Azure"

# Push to GitHub (you may need to authenticate)
git push origin main
```

**Note:** If you get a 403 error, authenticate with:
```powershell
# Use GitHub personal access token
git remote set-url origin https://YOUR_USERNAME:YOUR_TOKEN@github.com/saravanan9168/math.git
git push origin main
```

---

#### **Step 2: Create Azure Static Web App**

1. **Go to Azure Portal:**
   - Visit: https://portal.azure.com
   - Sign in with your account

2. **Create New Resource:**
   - Click "Create a resource"
   - Search for "Static Web App"
   - Click "Create"

3. **Configure Your Static Web App:**

   **Basics Tab:**
   ```
   Subscription: [Your Azure Subscription]
   Resource Group: Create new → "FibonacciCurveRG"
   Name: fibonacci-curve-generator
   Plan type: Free
   Region: (Choose closest to you, e.g., East US)
   ```

   **Deployment Details:**
   ```
   Source: GitHub
   GitHub Account: [Click "Sign in with GitHub"]
   Organization: saravanan9168
   Repository: math
   Branch: main
   ```

   **Build Details:**
   ```
   Build Presets: Custom
   App location: /
   Api location: (leave empty)
   Output location: (leave empty)
   ```

4. **Click "Review + Create"** then **"Create"**

5. **Wait 2-3 minutes** for deployment

6. **Get Your URL:**
   - After deployment, go to your resource
   - You'll see a URL like: `https://fibonacci-curve-generator-xxxxx.azurestaticapps.net`
   - Click it to view your site! 🎉

---

### ✅ **Your Site Will Be Live!**

Example URL: `https://fibonacci-curve-generator-abc123.azurestaticapps.net/index.html`

---

## 🌐 **Option 2: Azure Blob Storage (Static Website Hosting)**

### Why This Option?
- Simple and fast
- Very cost-effective
- Good for static files
- No GitHub required

### 📋 **Step-by-Step:**

#### **Step 1: Create Storage Account**

1. Go to **Azure Portal** → **Create a resource**
2. Search for "**Storage account**" → Click "Create"
3. Configure:
   ```
   Resource Group: FibonacciCurveRG (or create new)
   Storage account name: fibonaccicurve2024 (must be unique)
   Region: East US (or your preference)
   Performance: Standard
   Redundancy: LRS (Locally-redundant storage)
   ```
4. Click "**Review + Create**" → "**Create**"

#### **Step 2: Enable Static Website**

1. Go to your storage account
2. Left menu → **Data management** → **Static website**
3. Click "**Enabled**"
4. Set:
   ```
   Index document name: index.html
   Error document path: index.html
   ```
5. Click "**Save**"
6. **Copy the Primary endpoint URL** (e.g., `https://fibonaccicurve2024.z13.web.core.windows.net/`)

#### **Step 3: Upload Your File**

**Method A: Azure Portal (Easy)**
1. In Storage account → **Data storage** → **Containers**
2. Click on "**$web**" container
3. Click "**Upload**"
4. Select your `index.html` file
5. Click "**Upload**"

**Method B: Azure Storage Explorer (Recommended)**
1. Download **Azure Storage Explorer**: https://azure.microsoft.com/features/storage-explorer/
2. Install and sign in with your Azure account
3. Navigate to your storage account → **Blob Containers** → **$web**
4. Click "**Upload**" → "**Upload Files**"
5. Select `index.html` and upload

**Method C: Azure CLI**
```powershell
# Install Azure CLI first
az storage blob upload-batch --account-name fibonaccicurve2024 --source . --destination '$web' --pattern "*.html"
```

#### **Step 4: Access Your Website**

Visit your Primary endpoint URL:
```
https://fibonaccicurve2024.z13.web.core.windows.net/index.html
```

✅ **Your site is live!**

---

## 🔧 **Option 3: Azure App Service (More Features)**

### Why This Option?
- More control and features
- Can host multiple files
- Custom domains easier
- Good for scaling

### 📋 **Quick Deploy:**

#### **Step 1: Create App Service**

1. Azure Portal → **Create a resource** → **Web App**
2. Configure:
   ```
   Resource Group: FibonacciCurveRG
   Name: fibonacci-curve-app
   Publish: Code
   Runtime stack: Node 18 LTS (or any)
   Operating System: Linux
   Region: East US
   Pricing Plan: Free F1
   ```
3. Click "**Review + Create**" → "**Create**"

#### **Step 2: Deploy Using FTP**

1. Go to your App Service
2. Left menu → **Deployment** → **Deployment Center**
3. Choose "**FTP**" as source
4. Click "**Dashboard**"
5. Copy FTP credentials:
   ```
   FTP Endpoint: ftp://[your-app].azurewebsites.net
   Username: fibonacci-curve-app\$fibonacci-curve-app
   Password: [shown in portal]
   ```

#### **Step 3: Upload File**

Use **FileZilla** or **WinSCP**:
1. Connect using FTP credentials
2. Navigate to `/site/wwwroot/`
3. Upload `index.html`
4. Disconnect

#### **Step 4: Access**

Visit: `https://fibonacci-curve-app.azurewebsites.net/`

---

## 📊 **Comparison of Options**

| Feature | Static Web Apps | Blob Storage | App Service |
|---------|----------------|--------------|-------------|
| **Cost** | Free | ~$0.50/mo | Free tier |
| **Setup Time** | 5 mins | 3 mins | 5 mins |
| **HTTPS** | ✅ Auto | ✅ Auto | ✅ Auto |
| **Custom Domain** | ✅ Easy | ✅ Manual | ✅ Easy |
| **CI/CD** | ✅ GitHub | ❌ Manual | ✅ Multiple |
| **Global CDN** | ✅ Yes | ❌ Optional | ❌ Optional |
| **Best For** | Modern apps | Simple sites | Full apps |

**Recommendation:** Use **Azure Static Web Apps** for best experience! ⭐

---

## 🎯 **Quick Start Guide (Fastest Method)**

### **Deploy in 5 Minutes using Azure CLI:**

```powershell
# 1. Install Azure CLI
# Download from: https://aka.ms/installazurecliwindows

# 2. Login to Azure
az login

# 3. Create resource group
az group create --name FibonacciCurveRG --location eastus

# 4. Create storage account
az storage account create --name fibonaccicurve$RANDOM --resource-group FibonacciCurveRG --location eastus --sku Standard_LRS

# 5. Enable static website
az storage blob service-properties update --account-name fibonaccicurve$RANDOM --static-website --index-document index.html

# 6. Upload file
az storage blob upload --account-name fibonaccicurve$RANDOM --container-name '$web' --name index.html --file index.html --auth-mode login

# 7. Get URL
az storage account show --name fibonaccicurve$RANDOM --resource-group FibonacciCurveRG --query "primaryEndpoints.web" --output tsv
```

---

## 🔐 **Security & Best Practices**

### **1. HTTPS (Already Included)**
All Azure options provide automatic HTTPS ✅

### **2. Custom Domain (Optional)**

**For Static Web Apps:**
1. Go to your Static Web App → **Custom domains**
2. Click "**Add**" → Enter your domain (e.g., `fibonacci.yourdomain.com`)
3. Follow DNS verification steps
4. Azure handles SSL automatically

**For Blob Storage:**
1. Use **Azure CDN** for custom domain with HTTPS
2. Create CDN profile → Associate with storage
3. Configure custom domain

### **3. Performance Optimization**

Your site is already optimized because:
- ✅ No external dependencies
- ✅ Pure JavaScript (no frameworks)
- ✅ Single HTML file
- ✅ Lightweight (~14 KB)

---

## 📱 **After Deployment - Test Your Site**

### **Check These:**
1. ✅ Site loads correctly
2. ✅ Fibonacci Curve generates (try N=10)
3. ✅ Arithmetic operations work
4. ✅ Responsive on mobile
5. ✅ HTTPS works (green padlock)

### **Share Your Link:**
```
🌐 Live Demo: https://your-site.azurestaticapps.net/
📝 GitHub: https://github.com/saravanan9168/math
```

---

## 🐛 **Troubleshooting**

### **Issue: 404 Not Found**
**Solution:** 
- Make sure `index.html` is in the root directory
- Check file name is exactly `index.html` (lowercase)

### **Issue: Blank Page**
**Solution:**
- Open browser console (F12)
- Check for JavaScript errors
- Verify file uploaded correctly

### **Issue: GitHub Authentication Failed**
**Solution:**
```powershell
# Create Personal Access Token
# Go to GitHub → Settings → Developer settings → Personal access tokens
# Generate new token with 'repo' permissions
# Use token instead of password

git remote set-url origin https://YOUR_USERNAME:YOUR_TOKEN@github.com/saravanan9168/math.git
```

### **Issue: Azure CLI Not Found**
**Solution:**
Download from: https://aka.ms/installazurecliwindows

---

## 💰 **Costs (Estimated)**

### **Free Tier Limits:**

**Azure Static Web Apps:**
- Free: 100 GB bandwidth/month
- Cost: **$0.00** for most use cases

**Blob Storage:**
- First 5 GB: Free
- Storage: ~$0.02/GB/month
- Bandwidth: ~$0.087/GB
- **Typical cost: $0.50-$2/month**

**App Service:**
- Free tier: Available
- Cost: **$0.00** with F1 plan

**Recommendation:** Start with **Static Web Apps (Free)** ⭐

---

## 📧 **Submission Template**

Once deployed, use this template:

```
Subject: Fibonacci Curve Generator - Deployed on Azure

Dear [Instructor],

I have successfully deployed my Fibonacci Curve Generator to Microsoft Azure.

🌐 Live Demo: https://[your-app].azurestaticapps.net/
📦 GitHub Repository: https://github.com/saravanan9168/math
📝 Documentation: Included in repository (README.md, REPORT.md)

Deployment Details:
- Platform: Microsoft Azure Static Web Apps
- Hosting: Free tier
- HTTPS: Enabled
- Global CDN: Enabled
- Status: Live and operational

Features Implemented:
✅ Fibonacci Curve generation (individual arcs)
✅ Basic arithmetic operations
✅ Multi-color visualization
✅ Responsive design
✅ Works on all devices

Technology Stack:
- Frontend: HTML5, CSS3, JavaScript
- Backend: None (static site)
- Deployment: Azure Static Web Apps + GitHub Actions

The application is fully functional and meets all project requirements.

Thank you,
[Your Name]
```

---

## 🎉 **Success Checklist**

Before submitting, verify:
- [ ] Code pushed to GitHub
- [ ] Deployed to Azure
- [ ] Site loads correctly
- [ ] HTTPS works
- [ ] Fibonacci curve generates properly
- [ ] All arithmetic operations work
- [ ] Mobile responsive
- [ ] URL accessible publicly
- [ ] Documentation complete
- [ ] Screenshot taken (optional)

---

## 📞 **Need Help?**

### **Azure Documentation:**
- Static Web Apps: https://docs.microsoft.com/azure/static-web-apps/
- Blob Storage: https://docs.microsoft.com/azure/storage/blobs/storage-blob-static-website

### **Free Azure Credits:**
- Students: https://azure.microsoft.com/free/students/
- Free trial: https://azure.microsoft.com/free/

---

## 🚀 **You're All Set!**

Your Fibonacci Curve Generator is **ready for Azure deployment**!

**Recommended Path:**
1. ✅ Fix GitHub authentication (if needed)
2. ✅ Push code to GitHub
3. ✅ Create Azure Static Web App
4. ✅ Link to GitHub repository
5. ✅ Wait 2-3 minutes for auto-deployment
6. ✅ Get your live URL
7. ✅ Test your site
8. ✅ Submit with confidence!

**Expected Result:**
```
🎯 Live URL: https://fibonacci-curve-generator-xxxxx.azurestaticapps.net/
⏱️ Total Time: 5-10 minutes
💰 Cost: $0.00 (Free tier)
✅ Status: Production-ready
```

---

**Good luck with your deployment! 🎉**
