# CSC325 Group 4 Project
## Blood Pressure Monitoring & Alert System
Our capstone project focuses on building a Blood Pressure Monitoring & Health Tracker App, aimed at helping individuals monitor and manage their blood pressure levels daily. The app will be particularly beneficial for users with hypertension, elderly individuals, and those under long-term health supervision. It will provide an intuitive and mobile-friendly platform for users to record their systolic and diastolic readings, track changes over time, receive health alerts, and share reports with healthcare providers.

The app will feature an easy-to-use interface where users can log in, input blood pressure data, and visualize their readings through graphs and trend lines. Daily reminders and push notifications will prompt users to take readings consistently. The system will also provide recommendations based on the input values—for example, notifying users if their readings fall outside of a healthy range and encouraging them to consult a physician. Data can be exported as a csv file.

The project will be built using JavaFX and Firebase and will be developed in NetBeans. We will use GitHub for version control and collaboration, and YouTrack to assign and track tasks. A basic design for the interface will be created using Figma, and the full system will follow the WRSPM model for planning and documentation. 

## REQUIREMENTS:
### resources/files should contain:
- FirebaseAPI.json with variables "apiKey" and "projectId"
- key.json from the firebase service accounts adminsdk


### update Cloud Firestore rules to match the following: 
rules_version = '2';

service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
## WRSPM: 
#### W – World Assumptions
Users have access to a smartphone or tablet and basic digital literacy.

Users are health-conscious and want to manage blood pressure effectively. 

Some users may have physical or visual impairments, so the UI should be accessible. Users may need to share their readings with doctors. 

#### R – User Requirements 
Ability to register/login securely. 

Input blood pressure readings manually. 

View a log of historical readings with timestamps. 

Receive alerts for abnormal values. 

Get reminders to check BP. 

Export or share reports with physicians. 

#### S – Specifications and Interface Needs 
Clean, mobile-friendly UI with large fonts and icons. 

Firebase. 

Notification system for reminders. 

Admin functionality to manage health insights. 

Export to csv for sharing capability. 

#### P – Program
JavaFX. 

Firebase. 

NetBeans for development. 

GitHub for source control and versioning. 

YouTrack for sprint planning, bug tracking, and task management. 

#### M – Machine
Any modern laptop with Java SDK 14+ and NetBeans. 

Internet access for Firebase and GitHub integrations.


### [Figma](https://www.figma.com/files/team/1517314258344349520/project/403038466/Team-project?fuid=1517314590848677779)

