package com.army.jetpack.paging

/**
 * @author daijun
 * @date 2020/2/24
 * @description
 */
class WanAndroidBean {
    var data: DataBean = DataBean()
    var errorCode: Int = 0
    var errorMsg: String = ""

    class DataBean {
        var curPage: Int = 0
        var offset: Int = 0
        var over: Boolean = false
        var pageCount: Int = 0
        var size: Int = 0
        var total: Int = 0
        var datas: List<DatasBean> = emptyList()

        class DatasBean {
            var apkLink: String = ""
            var audit: Int = 0
            var author: String = ""
            var canEdit: Boolean = false
            var chapterId: Int = 0
            var chapterName: String = ""
            var collect: Boolean = false
            var courseId: Int = 0
            var desc: String = ""
            var descMd: String = ""
            var envelopePic: String = ""
            var fresh: Boolean = false
            var id: Int = 0
            var link: String = ""
            var niceDate: String = ""
            var niceShareDate: String = ""
            var origin: String = ""
            var prefix: String = ""
            var projectLink: String = ""
            var publishTime: Long = 0
            var selfVisible: Int = 0
            var shareDate: Long = 0
            var shareUser: String = ""
            var superChapterId: Int = 0
            var superChapterName: String = ""
            var title: String = ""
            var type: Int = 0
            var userId: Int = 0
            var visible: Int = 0
            var zan: Int = 0
            var tags: List<Tag> = emptyList()

            class Tag {
                var name = ""
                var url = ""
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as DatasBean

                if (apkLink != other.apkLink) return false
                if (audit != other.audit) return false
                if (author != other.author) return false
                if (canEdit != other.canEdit) return false
                if (chapterId != other.chapterId) return false
                if (chapterName != other.chapterName) return false
                if (collect != other.collect) return false
                if (courseId != other.courseId) return false
                if (desc != other.desc) return false
                if (descMd != other.descMd) return false
                if (envelopePic != other.envelopePic) return false
                if (fresh != other.fresh) return false
                if (id != other.id) return false
                if (link != other.link) return false
                if (niceDate != other.niceDate) return false
                if (niceShareDate != other.niceShareDate) return false
                if (origin != other.origin) return false
                if (prefix != other.prefix) return false
                if (projectLink != other.projectLink) return false
                if (publishTime != other.publishTime) return false
                if (selfVisible != other.selfVisible) return false
                if (shareDate != other.shareDate) return false
                if (shareUser != other.shareUser) return false
                if (superChapterId != other.superChapterId) return false
                if (superChapterName != other.superChapterName) return false
                if (title != other.title) return false
                if (type != other.type) return false
                if (userId != other.userId) return false
                if (visible != other.visible) return false
                if (zan != other.zan) return false
                if (tags != other.tags) return false

                return true
            }

            override fun hashCode(): Int {
                var result = apkLink.hashCode()
                result = 31 * result + audit
                result = 31 * result + author.hashCode()
                result = 31 * result + canEdit.hashCode()
                result = 31 * result + chapterId
                result = 31 * result + chapterName.hashCode()
                result = 31 * result + collect.hashCode()
                result = 31 * result + courseId
                result = 31 * result + desc.hashCode()
                result = 31 * result + descMd.hashCode()
                result = 31 * result + envelopePic.hashCode()
                result = 31 * result + fresh.hashCode()
                result = 31 * result + id
                result = 31 * result + link.hashCode()
                result = 31 * result + niceDate.hashCode()
                result = 31 * result + niceShareDate.hashCode()
                result = 31 * result + origin.hashCode()
                result = 31 * result + prefix.hashCode()
                result = 31 * result + projectLink.hashCode()
                result = 31 * result + publishTime.hashCode()
                result = 31 * result + selfVisible
                result = 31 * result + shareDate.hashCode()
                result = 31 * result + shareUser.hashCode()
                result = 31 * result + superChapterId
                result = 31 * result + superChapterName.hashCode()
                result = 31 * result + title.hashCode()
                result = 31 * result + type
                result = 31 * result + userId
                result = 31 * result + visible
                result = 31 * result + zan
                result = 31 * result + tags.hashCode()
                return result
            }

        }
    }
}
