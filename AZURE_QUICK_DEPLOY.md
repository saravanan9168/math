# ⚡ Azure Quick Deploy - Cheat Sheet

## 🎯 **FASTEST METHOD - Azure Portal Upload (2 Minutes)**

### **Step 1: Create Storage Account**
1. Go to: https://portal.azure.com
2. Click: **Create a resource** → **Storage account**
3. Settings:
   - Name: `fibonaccicurve2024` (must be unique globally)
   - Performance: Standard
   - Redundancy: LRS
4. Click: **Review + Create** → **Create**

### **Step 2: Enable Static Website**
1. Open your storage account
2. Left menu → **Static website**
3. Click: **Enabled**
4. Index document: `index.html`
5. **Save**
6. **Copy the Primary endpoint URL** ← This is your website URL!

### **Step 3: Upload File**
1. Left menu → **Containers**
2. Click: **$web**
3. Click: **Upload**
4. Select: `index.html` from your folder
5. Click: **Upload**

### **Step 4: TEST**
Visit your URL: `https://fibonaccicurve2024.z13.web.core.windows.net/`

✅ **DONE! Your site is live!**

---

## 🌟 **BEST METHOD - Azure Static Web Apps (5 Minutes)**

### **Requirements:**
- GitHub account
- Code pushed to GitHub

### **Steps:**

1. **Push to GitHub:**
```powershell
cd "c:\Users\91936\OneDrive\Documents\ZP intern"
git add .
git commit -m "Deploy to Azure"
git push origin main
```

2. **Create Static Web App:**
   - Portal → **Create a resource** → **Static Web App**
   - Name: `fibonacci-curve`
   - Region: East US
   - Source: GitHub
   - Sign in to GitHub
   - Select: Repository `math`, Branch `main`
   - App location: `/`
   - Click: **Create**

3. **Wait 2-3 minutes**

4. **Get URL from resource overview**

✅ **DONE! Auto-deployed with CI/CD!**

---

## 🔑 **GitHub Authentication Fix**

If `git push` fails with 403 error:

### **Option 1: Personal Access Token**
```powershell
# 1. Create token at: https://github.com/settings/tokens
# 2. Click "Generate new token (classic)"
# 3. Select 'repo' scope
# 4. Copy the token
# 5. Use it:

git remote set-url origin https://YOUR_USERNAME:YOUR_TOKEN@github.com/saravanan9168/math.git
git push origin main
```

### **Option 2: GitHub CLI**
```powershell
# Install: https://cli.github.com/
gh auth login
git push origin main
```

### **Option 3: Re-authenticate**
```powershell
git config --global credential.helper wincred
git push origin main
# Enter credentials when prompted
```

---

## 📋 **Quick Commands Reference**

### **Check Git Status:**
```powershell
cd "c:\Users\91936\OneDrive\Documents\ZP intern"
git status
```

### **Add All Files:**
```powershell
git add .
git commit -m "Ready for deployment"
```

### **View Remote URL:**
```powershell
git remote -v
```

### **Open in Browser:**
```powershell
# Open index.html locally
start index.html

# Or
explorer index.html
```

---

## 🎯 **Your Live URLs (After Deployment)**

### **Blob Storage Example:**
```
https://fibonaccicurve2024.z13.web.core.windows.net/
```

### **Static Web Apps Example:**
```
https://fibonacci-curve-xxxxx.azurestaticapps.net/
```

### **App Service Example:**
```
https://fibonacci-curve-app.azurewebsites.net/
```

---

## ✅ **Verification Checklist**

After deployment, test:
- [ ] Site loads (no 404)
- [ ] Select "Fibonacci Curve"
- [ ] Enter N = 10
- [ ] Click "Calculate"
- [ ] See colored curves
- [ ] Try arithmetic operations
- [ ] Test on mobile device
- [ ] Check HTTPS (green lock)

---

## 🐛 **Common Issues & Fixes**

| Issue | Fix |
|-------|-----|
| 404 Not Found | Ensure file is named exactly `index.html` |
| Blank page | Check browser console (F12) for errors |
| Can't push to GitHub | Use Personal Access Token |
| Azure login fails | Use incognito/private browsing |
| File won't upload | Check file size (should be ~14 KB) |

---

## 💰 **Cost Estimate**

| Service | Monthly Cost |
|---------|--------------|
| Static Web Apps (Free tier) | **$0.00** |
| Blob Storage | **$0.50 - $2.00** |
| App Service (F1) | **$0.00** |

**Recommendation:** Use Static Web Apps for $0 cost! ⭐

---

## 📞 **Quick Links**

- **Azure Portal:** https://portal.azure.com
- **GitHub:** https://github.com/saravanan9168/math
- **Azure Free Account:** https://azure.microsoft.com/free/
- **Azure CLI Download:** https://aka.ms/installazurecliwindows
- **GitHub Token:** https://github.com/settings/tokens

---

## 🎉 **One-Command Deploy (If Azure CLI Installed)**

```powershell
# This creates everything automatically:
az staticwebapp create `
  --name fibonacci-curve `
  --resource-group FibonacciCurveRG `
  --source https://github.com/saravanan9168/math `
  --location "East US 2" `
  --branch main `
  --app-location "/" `
  --login-with-github
```

---

## 📱 **Share Your Deployment**

```
🎉 My Fibonacci Curve Generator is live!
🌐 URL: [Your Azure URL]
💻 Code: https://github.com/saravanan9168/math
📚 Docs: Included in repository
```

---

**⏱️ Total Time: 2-5 minutes**  
**💰 Cost: $0.00 (Free tier)**  
**🎯 Status: Production-ready**  

**You got this! 🚀**
