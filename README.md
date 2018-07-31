
### Git 学习笔记 ###

**基础学习**

**git init** 

初始化一个文件为一个库（需要进入文件夹里面把这个文件夹初始化为一个仓库，会生成一个`.git`的隐藏文件夹）


**git add**

将一个文件添加到git的缓存区，如：git add filename ,将文件添加到git的缓存区

**git commit -m**

将缓存区的文件添加到本地仓库，一般需要add文件后再执行commit.如：git commit -m "message" ,这里的message是提交时需要注明的log，一般必须要注明，以便于以后查看提交信息

**git commit --amend**

`git commit --amend -m "message"` 使用新的一次的commit，替代上一次的提交，如果代码没有任何改变，则用来改写上一次的commit提交信息


**git commit --amend <fileName>**

重新做一次commit，并包括指定文件的新变化，用来覆盖上一次的commit的提交

**git status**

git status用于查看现在工作区是否有未提交或未添加的更改，一般commit前先看下，以免提交出现错误。如：
    
    $ git status
	On branch master
	nothing to commit, working tree clean
此时工作区clean,没有需要提交的commit
	
	$ git status
	On branch master
	Changes not staged for commit:
  	(use "git add <file>..." to update what will be committed)
  	(use "git checkout -- <file>..." to discard changes in working directory)

        modified:   helloworld.kt

	no changes added to commit (use "git add" and/or "git commit -a")

此时改变了文件，却未add和commit的状态

**git diff**

用于比较工作区和缓存区的diff，若工作区的内容未add到缓存区，则会显示工作区和缓存区的不同，若已经add了，则不会显示不同。此时查看不同需要用到git diff --cached来查看仓库和缓存区的不同

**git diff --cacehed**

用于查看已经添加到缓存区后，未commit的情况下，仓库和缓存区的不同

**git log**

用于查看提交的版本的信息，但是git log只能看现在head指向的版本之前的版本信息。如果进行了回退，就看不到之前提交的信息了。如：

    $ git log
	commit e141e7650254fac81decbc890e86d58cd72bf872 (HEAD -> master)
	Author: cwh <cwh_semal@163.com>
	Date:   Wed Jul 25 18:37:48 2018 +0800

    	add String for diff and diff --cached

	commit 5c70e7486032e6361a4b6128ceaa29240833d518
	Author: cwh <cwh_semal@163.com>
	Date:   Wed Jul 25 18:27:32 2018 +0800

    	add a new file and add a new vim String


**git reflog**

用于查看你的每一次的对于版本仓库的修改的命令，可用于查看回退的版本。如：


	$ git reflog
	e141e76 (HEAD -> master) HEAD@{0}: commit: add String for diff and diff --cached
	5c70e74 HEAD@{1}: commit: add a new file and add a new vim String
	3d01df6 HEAD@{2}: reset: moving to 3d01df6
	373f301 HEAD@{3}: reset: moving to HEAD^
	3d01df6 HEAD@{4}: reset: moving to 3d01df61
	373f301 HEAD@{5}: reset: moving to HEAD^
	3d01df6 HEAD@{6}: commit: add a Vim Editor Text
	373f301 HEAD@{7}: commit: change helloworld file
	1b0ee74 HEAD@{8}: commit: add two Kotlin file
	ed6251e HEAD@{9}: commit (initial): write a readme file

**Git reset --hard HEAD^**

版本回退到前一个版本，Git reset --hard HEAD~10 回退十个版本

git reset --hard 3d01df6 回退到一个指定的版本


**工作区和暂存区**

这里我理解工作区即为我们所存储文件的目录指定未位置，暂存区指的是我们已经通过add指令放文件的位置，还未commit到本地仓库，而本地仓库是指我们原始版本未经过修改的本地仓库，其中通过commit提交的就都是提交到本地仓库。

**git 中针对提交的是每次的修改**

git中每次提交的都是针对修改，而不是文件，若你修改了文件，未进行add ,那么提交修改时，是不会提交此次对文件的修改的。

**关于撤销与修改**

`git checkout -- <filename>` 用来撤销工作区的修改。会让文件回到上一次`add`或者是`commit`时的状态，至于是回到`add` 或者`commit`时的状态需要看就近原则。（优先回复暂存区的，若暂存区没有则回复`commit`时的状态）

`git reset HEAD <filename>` 用来撤销暂存区的内容。即有时`add`之后想重新回到原来状态，可以通过此命令来将暂存区的修改内容撤销掉，此时暂存区为空。

理解暂存区内容的概念后，这两个命令可以相互协作使用，来达到想要撤销的目的。

对于保存到暂存区的内容的撤销可以直接通过`git reset --hard HEAD`的方式直接恢复到最近一次`commit`的时候，则暂存区内容消失

**对于文件删除的恢复**

对于文件的删除其实也是修改，也要经过暂存区，所以对于删除的，未`add`到暂存区的可以通过`git checkout -- <filename>`的形式将其还原，对于已经`add`到暂存区的，可以通过`get reset HEAD <filename>` 将暂存区清空，在用`git checkout -- <filename>` 将删除文件恢复到之前仓库里的状态。

对于提交删除文件，还有一个命令`git rm`,其代表的是删除文件，并提交到了暂存区，所以此时需要先`git reset HEAD <filename>` 清空缓存区，再通过`git checkout -- <filename>` 来将删除文件恢复回来

**git 添加远程仓库**

这里把github作为远程仓库，首先需要在github创建自己的仓库。
通过 `git remote add origin git@github.com:github_accountName/repoName.git`来将远程仓库与已有的仓库进行连接起来。

连接本地仓库与远程仓库 `origin` 相当于远程仓库的名字 ，一般默认叫这个。

通过`git push -u origin master`将本地master分支的内容push到远程仓库的master分支，并建立本地master与远程master分支的联系（-u 即为分支建立联系）

在git push的过程中需要ssh key来验证，才能push成功，所以需要在githu上添加自己本机的ssh key,其中ssh key的生成与配置方法如下链接：

[https://www.cnblogs.com/horanly/p/6604104.html](https://www.cnblogs.com/horanly/p/6604104.html "SSH KEY的生成方法")


配置好后就可以他用过git push将本文件push到github仓库了（对于自己在github生成的仓库含有本地仓库没有的文件时，需要先将远程仓库pull下来进行合并，在push上去 其中pull下来并合并的命令是：`git pull --rebase origin master`）

**克隆远程仓库到本地**

一般的开发步骤是，克隆远程分支到本地进行开发，开发完成后再提交到远程仓库。克隆仓库的命令是`git clone git@github.com:github_accountName/repoName.git`,将远程仓库克隆到本地（会形成一个以仓库为名称的文件夹）

**创建与分支的合并**

git中一般会有默认的master分支，其中HEAD指向当前所用的分支，在默认情况下指向master分支。

git创建分支 `git branch dev`创建Dev分支，切换到Dev分支：`git checkout dev` ,创建和切换分支可以合并为一局`git checkout -b dev`创建并切换到dev分支。此时HEAD指向dev分支，图如下：


![](https://cdn.liaoxuefeng.com/cdn/files/attachments/001384908811773187a597e2d844eefb11f5cf5d56135ca000/0)

其实分支的创建只是添加了一个新的dev指针和将HEAD指向了dev分支

对于在dev分支开发完后，进行分支的合并，完成代码合并
git merge dev 将dev分支合并到当前分支（这里没有考虑分支合并冲突的问题）。如下：

![](https://cdn.liaoxuefeng.com/cdn/files/attachments/00138490883510324231a837e5d4aee844d3e4692ba50f5000/0)

`git branch` 查看分支

`git branch <name>` 创建分支

`git checkout <name>` 切换分支

`git checkout -b <name>` 切换并创建分支

`git merge <name>` 合并某分支到当前分支

`git branch -d <name>` 删除某分支

 `git branch -r `列出所有远程分支

`git branch -a`列出所有分支，包括本地和远程分支

`git branch <name> <commitId>` 创建一个新的分支，指向一个指定commit时的内容。（这样做的好处是，可以在切出新的feature分支时，指定从哪个commit切出，便于开发）

`git checkout -`切换到上一个分支

`git branch --track <name> <origin/name>`创建一个新的分支，并与远程指定分支建立联系

`git branch --set-upstream <branchName> <origin/name>`,指定已存在的分支与远程分支建立联系

`git push origin --delete <branchName>` 删除远程仓库指定的分支。如：`git push origin --delete dev2`删除远程的dev2分支

`git branch -dr <origin/name>` 删除远程分支与本地分支间的联系

`git cherry-pick <commitId>`选择一个指定的commit合并到当前分支




**解决冲突**

对于在分支上开发了完成后，需要合并到master分支上，此时若在master分支上也有过改动，那么在合并时会出现冲突。

其中合并分支的命令是`git merge dev`将dev分支合并到当前分支上，会出现合并分支的冲突的情况。如：

    $ git merge dev
	Auto-merging helloworld.kt
	CONFLICT (content): Merge conflict in helloworld.kt
	Automatic merge failed; fix conflicts and then commit the result.

此时可以通过git merge --abort来放弃此次的分支的合并，手动去解决冲突后，再次合并。
也可以不放弃此次的合并，手动去解决冲突的地方，此时冲突的地方会有代码标识出来，如：

    $ cat helloworld.kt
	fun main(args:Array<String>){

        println("Hello World Again!!")
        println("Hello World!!!)
        println("Open Hello World With Vim!!!")
        println("Add a New String!!")
        println("to check git diff")
        println("add This For Remote Repo")
        println("add This For Merge Branch")
	<<<<<<< HEAD
        println("For See Commit Tips")
        println("For merge Branch")

	=======
        println("add This For Merge Dev Branch")
	>>>>>>> dev
	}
通过手动解决后，在进行commit，此时主分支的代码即为最新的代码，此时可以push到远程分支。

对于dev分支，此时的内容未dev分支上的内容，并没有和master分支一致，是原dev分支上的内容，此时可以接着开发，下次再次进行合并。


**分支管理策略**

对于分支的和并，如果咋dev分支和master分支原来一样的前提下，在dev分支进行修改后，进行合并，此时不会冲突，会自动以fast-forward的方式进行合并，此时的合并，只是改变了master指针的位置，并没有进行提交之类的操作，这样在删除dev分支后无法看到提交记录，无法回退。所以在合并分支时一变建议不用fast-forward模式进行提交，因为以后可能没有记录。

所以一般用`git merge --no-ff -m "merge message" dev`方式来合并分支，此时不是fast-forward模式，此时不会出现没有记录的情况，应为此时的合并其实是，将dev的内容添加到master分支，然后再进行了一次commit的操作，所以此时会有记录。

`git merge --no-ff -m "merge message" dev` 通过commit的方式提交合并。

**分支开发策略**

一般来说，不会再master分支上开发，会在dev分支上切出各个feature分支来进行开发，开发完后，合并到dev分支，在dev进行测试与修改，修改完善后，再把dev分支的内容合并到master分支发布新版本。如：

![](https://cdn.liaoxuefeng.com/cdn/files/attachments/001384909239390d355eb07d9d64305b6322aaf4edac1e3000/0)




**Bug分支的管理**

对于bug的修复，一般需要新开一个分支进行修改和提交，但是有时候，dev分支正在进行开发中，不想线提前commit，这时可以用到`git status`来进行处理。

`git stash`当于把当前的分支的内容储藏起来，下次回来时，接着修改。(这里说明一下，最好将dev中的内容add到暂存区后再进行stash)。由于所有分支公用一个工作区和一个暂存区，这样做，可以避免修改时混淆，在暂存区通过stash来把修改内容在暂存区隐藏起来，然后再切换到其他分支进行内容的修改与合并。

恢复stash的内容，切换到dev分支接着进行开发，此时需要通过`git stash list`来查看自己以前存储过的工作现场，然后通过`git stash pop`恢复工作现场。

    git stash apply 恢复工作现场，单没删除记录
	git stash drop 删除记录
	git stash pop 恢复现场并删除记录


其实对于这种情况我自己更偏向于将dev commit一下，毕竟自己开发分支，自己怎么开发都可以。然后切换到master分支，checkout出bug分支，修改问题，合并到master分支，然后push，再回到dev分支进行开发。

这里需要了解到一点工作区 和 暂存区是公用的，所以改变后会导致不止要commit到那个分支上。

**feature分支**

一般开发一个新功能时，一般时checkout出一个feature分支进行开发，在开发完后，何如主的dev分支，这样就可以做到分开功能开发。而后再讲本地主dev分支与远程dev分支合并，从而完成开发，而且也便于回退和功能分支的切换。
对于工能分支开发完成后不需要该功能，可以删除该分支，但需要强制删除
    git branch -D branchName 强制删除未合并的分支，但删除后无法找回
这里建议保留该分支，也不删除，以便以后需要。


**多人协作**

多人协作时会出现向同一分支push代码的情况，就会出现代码冲突的情况，所以需要进行一些合并的操作。

对于远程库信息的查看：

 `git remote` 查看晕车仓库名
	
	$ git remote
	origin

    
`git remote -v` 查看拉取和推送代码地址
    
    $ git remote -v
	origin  git@github.com:cwh-github/GitStudy.git (fetch)
	origin  git@github.com:cwh-github/GitStudy.git (push)

分支的推送

`git push origin master`  把该分支上的所有本地提交推送到远程库。其中的master指的是本地分支，推送时需要指定推送什么分支到远程。

对于远程分支没有相应的分支时，此时push新的分支时，会在远程自动创建与之对应的同名的分支。


抓取分支

对于我们克隆一个项目到本地时，一般只是会克隆主分支到本地。

如：`git clone git@github.com:userAccount/projectName.git`,此时获取的项目只会有master分支。对于想创建对应远程dev分支的本地dev分支，可以用如下命令：

	git checkout -b dev origin/dev

创建对应远程dev分支的本地dev分支。

当在本地dev分支修改完成后，想推送到远程dev分支，直接用：

    git push origin dev

进行代码的推送，但是有时多人协作时，有人已经推送过了代码，此时就需要线pull远程代码，到本地，进行代码合并和冲突的处理，commit后，在push到远程


**git rebase**

rebase操作可以爸本地的未push的分叉提交历史整理成直线；

reabase的目的是使得我们在查看历史提交的变化时更容易。

**标签管理**

**创建标签**

个人感觉标签的作用是做一个标记，表明这是一个版本发布了，做一个标记，以便以后来查看版本的发布。

tag的创建：`git tag <tagname>`创建一个tag

查看所有的tag：`git tag` 

默认创建的标签是打在最新的提交上的，有时忘记了，想回头打tag，只需要找到commit的id即可

给对应commit id打tag：`git tag <tagname> <commitId>`

对于打标签时，也可以添加文字，来说明这个tag的含义：

`git tag -a <tagname> -m "message"`   说明标签含义


查看标签具体信息：`git show <tagname>`  

**注意**：标签总是和某个commit挂钩的，不管分支，所以如果某个master和dev分支都含有该commit，则master和dev分支都会有该tag


**操作标签**

删除标签：`git tag -d <tagname>`

推送标签到远程仓库：`git push origin <tagName>`

一次性推送全部尚未推送的标签：`git push origin --tags`

删除远程标签：1.先删除本地标签：`git tag -d <tagName>`  2 通过push命令删除远程的标签： `git push origin :refs/tags/<tagName>`

新建一个分支，指向指定tag：`git checkout -b <name> <tagName>`

**忽略特殊文件**

在同步工程的时候，有些工程自动生成的东西是不需要同步到代码库的，这时候，我们可以通过添加忽略规则来忽略一些不需要上传的文件。一般这个文件叫做.gitingore文件。

github已经为我们准备了各种的需要忽略的配置文件，我们一般只需要组合一下这些配置，进行忽略就可以了。所有的配置文件在一下网址：[https://github.com/github/gitignore](https://github.com/github/gitignore)

忽略的原则是：1.忽略系统自动生成的文件，如：缩略图等。2.忽略编译时生成的中间文件、可执行文件等。3.忽略自己的带有敏感信息的配置文件，如：存放口令的配置文件。

对于忽略的一类文件，有部分需要添加到git里，可以用如下命令强制添加。如：

    git add -f App.class
来说明添加该文件

检查gitignore的规则，：`git check-ignore -v App.class` 

**git 中别名的配置**

git中别名的配置的好处是可以简化一些命令，这样更加方便。

如：`git config --global alias.st status` 相当于将status命令简化为st，即`git status==git st`效果一样

较好用的一个为查看log的，如下：`git config --global alias.lg "log --color --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit"`，查看提交历史比较清楚。

配置时加上 --global，即该配置时针对整台电脑的，不加时，只针对当前仓库可用。

对于配置都在.git/config文件下，对于不想用了的别名指令，删除哪一行即可。如：

	$ cat .git/config 
	[core]
    	repositoryformatversion = 0
    	filemode = true
    	bare = false
    	logallrefupdates = true
    	ignorecase = true
    	precomposeunicode = true
	[remote "origin"]
    	url = git@github.com:michaelliao/learngit.git
    	fetch = +refs/heads/*:refs/remotes/origin/*
	[branch "master"]
    	remote = origin
    	merge = refs/heads/master
	[alias]
    	last = log -1

对于全局的git别名配置的文件在 用户/用户名/.gitconfig文件中。（通过everthing即可以搜索出来）

好的别名可以减少很多时间。


**常用查看信息命令**

`git status` 显示文件状态及变化

`git log` 显示当前分支的版本历史

`git log --stat`显示commit历史，和每次commit发生变化的文件

`git log <commitId> HEAD --pretty=format:%s`显示某个commit之后的所有变动的cmmmit信息

`git log <commitId> HEAD --grep="keyWord"` 显示某个commit之后的所有变动，且必须包含关键字 keyword

`git log --follow <fileName>` 显示某个文件的版本历史，包括文件改名

`git log -p <fileName>`显示指定文件相关的每一次的diff

`git log -<times> --pretty --oneline` 显示过去的times次提交

`git shortlog -sn` 显示所有提交过的用户，按提交次数排序

`git blame <fileName>` 显示指定文件是什么用户在什么时候修改过

`git diff HEAD`显示工作区与当前分支最新commit间的差异

`git show <commitId>`显示某次提交的数据变化内容

`git show --name-oonly <commitId>`显示某次提交发生变化的文件


**远程同步**

`git fetch <origin> <branchName>`拉取远程指定分支的内容到本地，此时会自动形成一个新的分支**FETCH_BRANCH**，次分支就是最新的远程指定分支的内容，让后可以切换到master分支与这个分支进行合并，从而来解决冲突。

`git remote -v`显示远程仓库信息

`git remote show <origin>` 显示远程仓库的具体信息，如：

	$ git remote show origin
	* remote origin
	  Fetch URL: git@github.com:cwh-github/GitStudy.git
	  Push  URL: git@github.com:cwh-github/GitStudy.git
	  HEAD branch: master
	  Remote branches:
	    dev    tracked
	    master tracked
	  Local branches configured for 'git pull':
	    dev    merges with remote dev
	    master merges with remote master
	  Local refs configured for 'git push':
	    dev    pushes to dev    (fast-forwardable)
	    master pushes to master (fast-forwardable)

`git pull <origin> <branchName>` 取回远程仓库的某分子，并与本地分支合并

`git push <origin> <branchName>` 上传某分子到远程仓库对应的分子，如果远程没有该分支，创建该分支

`git push <origin>` --force 强行推送当前分支到远程仓库，即使有冲突

`git push <origin> --all` 推送所有分支到远程仓库

**撤销**

`git checkout <commitId> <fileName>` 恢复某个commit的指定文件到暂存区和工作区

`git checkout .` 恢复所有上一次add或没add时commit的内容到工作区

`git reset --keep <commitId>`重置当前分支到指定commit，保持暂存区和工作区不变

`git revert <commitId>` 新建一个commit，来撤销指定的commit，后者的所有变化被前者抵消，并且应用到当前分支

`git reset --soft` 重置时,暂存区和工作目录不会改变

`git reset --mixed` 默认选项。暂存区和指定的commit一样，单工作区不变

`git reset --hard` 暂存区和工作区都同步到指定的提交

`git checkout <commitId>` 切换到某次提交（这样可以在这里开一个分支进行feature的开发）







**git 常用命令速查表**

![](https://pic4.zhimg.com/v2-d65f30212ad48099391577064047ecdf_r.jpg)















